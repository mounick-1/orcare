import React, { createContext, useContext, useState, ReactNode } from 'react';
import { sendGeminiMessage, ChatTurn } from '../api/apiService';

export interface ChatMessage {
  id: string;
  text: string;
  isFromUser: boolean;
  timestamp: Date;
}

interface ChatContextType {
  messages: ChatMessage[];
  isTyping: boolean;
  sendMessage: (text: string) => Promise<void>;
  sendSymptomQuery: (symptom: string) => Promise<void>;
  startNewSession: () => void;
}

const ChatContext = createContext<ChatContextType | null>(null);

export function ChatProvider({ children }: { children: ReactNode }) {
  const [messages, setMessages] = useState<ChatMessage[]>([
    {
      id: 'welcome',
      text: "Hello! I'm ORCare AI, your friendly oral health assistant. How can I help you today? You can ask me about symptoms, oral hygiene, or dental conditions.",
      isFromUser: false,
      timestamp: new Date(),
    },
  ]);
  const [isTyping, setIsTyping] = useState(false);
  const [history, setHistory] = useState<ChatTurn[]>([]);

  function addMessage(text: string, isFromUser: boolean) {
    const msg: ChatMessage = {
      id: Date.now().toString(),
      text,
      isFromUser,
      timestamp: new Date(),
    };
    setMessages((prev) => [...prev, msg]);
    return msg;
  }

  async function sendMessage(text: string) {
    const trimmed = text.trim();
    if (!trimmed) return;

    addMessage(trimmed, true);
    setIsTyping(true);

    const newTurn: ChatTurn = { role: 'user', parts: [{ text: trimmed }] };

    try {
      const reply = await sendGeminiMessage(history, trimmed);
      addMessage(reply, false);
      setHistory((prev) => [
        ...prev,
        newTurn,
        { role: 'model', parts: [{ text: reply }] },
      ]);
    } catch (e: any) {
      addMessage('Sorry, I encountered an error. Please try again.', false);
    } finally {
      setIsTyping(false);
    }
  }

  async function sendSymptomQuery(symptom: string) {
    const query = `I am experiencing ${symptom}. What could be causing this, and what should I do?`;
    await sendMessage(query);
  }

  function startNewSession() {
    setMessages([
      {
        id: 'welcome-' + Date.now(),
        text: "Hello! I'm ORCare AI, your friendly oral health assistant. How can I help you today?",
        isFromUser: false,
        timestamp: new Date(),
      },
    ]);
    setHistory([]);
  }

  return (
    <ChatContext.Provider value={{ messages, isTyping, sendMessage, sendSymptomQuery, startNewSession }}>
      {children}
    </ChatContext.Provider>
  );
}

export function useChat() {
  const ctx = useContext(ChatContext);
  if (!ctx) throw new Error('useChat must be used inside ChatProvider');
  return ctx;
}
