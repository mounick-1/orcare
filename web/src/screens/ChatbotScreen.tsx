import React, { useState, useRef, useEffect } from 'react';
import {
  View, Text, StyleSheet, FlatList, TextInput,
  TouchableOpacity, KeyboardAvoidingView, ActivityIndicator,
} from 'react-native';
import { useNavigation } from '@react-navigation/native';
import { Colors } from '../theme/colors';
import { useChat } from '../context/ChatContext';

const suggestions = [
  '🪥 How do I brush properly?',
  '🩸 Why do my gums bleed?',
  '💨 What causes bad breath?',
  '🧵 How often should I floss?',
  '❄️ What is tooth sensitivity?',
];

export default function ChatbotScreen({ navigation: navProp, route }: any) {
  const navHook = useNavigation() as any;
  const navigation = navProp ?? navHook;
  const canGoBack = navigation?.canGoBack?.() ?? false;
  const { symptomName } = (route?.params) ?? {};
  const { messages, isTyping, sendMessage, sendSymptomQuery, startNewSession } = useChat();
  const [input, setInput] = useState('');
  const [focused, setFocused] = useState(false);
  const flatListRef = useRef<FlatList>(null);
  const initiated = useRef(false);

  useEffect(() => {
    if (symptomName && !initiated.current) {
      initiated.current = true;
      sendSymptomQuery(symptomName);
    }
  }, [symptomName]);

  useEffect(() => {
    setTimeout(() => flatListRef.current?.scrollToEnd({ animated: true }), 100);
  }, [messages]);

  async function handleSend() {
    const text = input.trim();
    if (!text) return;
    setInput('');
    await sendMessage(text);
  }

  return (
    <KeyboardAvoidingView style={styles.container} behavior="padding">
      {/* Header */}
      <View style={styles.header}>
        {canGoBack ? (
          <TouchableOpacity onPress={() => navigation.goBack()} style={styles.backBtn}>
            <Text style={styles.backArrow}>←</Text>
          </TouchableOpacity>
        ) : (
          <View style={styles.headerLogo}>
            <Text style={styles.headerLogoStar}>✦</Text>
          </View>
        )}

        <View style={styles.aiInfo}>
          <View style={styles.aiAvatarBox}>
            <Text style={styles.aiAvatarStar}>✦</Text>
            <View style={styles.onlineDot} />
          </View>
          <View>
            <Text style={styles.aiName}>ORCare AI</Text>
            <Text style={styles.aiStatus}>● Online · Oral Health Assistant</Text>
          </View>
        </View>

        <TouchableOpacity style={styles.newChatBtn} onPress={startNewSession}>
          <Text style={styles.newChatText}>+ New</Text>
        </TouchableOpacity>
      </View>

      {/* Messages */}
      <FlatList
        ref={flatListRef}
        data={messages}
        keyExtractor={(item) => item.id}
        contentContainerStyle={styles.messageList}
        showsVerticalScrollIndicator={false}
        renderItem={({ item }) => (
          <View style={[styles.bubbleRow, item.isFromUser && styles.bubbleRowUser]}>
            {!item.isFromUser && (
              <View style={styles.aiBubbleAvatar}>
                <Text style={styles.aiBubbleAvatarStar}>✦</Text>
              </View>
            )}
            <View style={[styles.bubble, item.isFromUser ? styles.userBubble : styles.aiBubble]}>
              <Text style={[styles.bubbleText, item.isFromUser && styles.userBubbleText]}>
                {item.text}
              </Text>
              <Text style={[styles.timestamp, item.isFromUser && styles.userTimestamp]}>
                {item.timestamp.toLocaleTimeString([], { hour: '2-digit', minute: '2-digit' })}
              </Text>
            </View>
          </View>
        )}
        ListFooterComponent={
          isTyping ? (
            <View style={styles.bubbleRow}>
              <View style={styles.aiBubbleAvatar}>
                <Text style={styles.aiBubbleAvatarStar}>✦</Text>
              </View>
              <View style={[styles.bubble, styles.aiBubble, styles.typingBubble]}>
                <ActivityIndicator size="small" color={Colors.primary} />
                <Text style={styles.typingText}>Thinking…</Text>
              </View>
            </View>
          ) : null
        }
      />

      {/* Suggestions — shown only on fresh chat */}
      {messages.length <= 1 && (
        <View style={styles.suggestions}>
          <View style={styles.suggestHeader}>
            <Text style={styles.suggestStar}>✦</Text>
            <Text style={styles.suggestLabel}>Suggested questions</Text>
          </View>
          <View style={styles.chips}>
            {suggestions.map((s) => (
              <TouchableOpacity
                key={s}
                style={styles.chip}
                onPress={() => sendMessage(s.slice(2).trim())}
                activeOpacity={0.7}
              >
                <Text style={styles.chipText}>{s}</Text>
              </TouchableOpacity>
            ))}
          </View>
        </View>
      )}

      {/* Input */}
      <View style={[styles.inputBar, focused && styles.inputBarFocused]}>
        <TextInput
          style={styles.input}
          placeholder="Ask about oral health…"
          placeholderTextColor={Colors.textMuted}
          value={input}
          onChangeText={setInput}
          onSubmitEditing={handleSend}
          onFocus={() => setFocused(true)}
          onBlur={() => setFocused(false)}
          returnKeyType="send"
          multiline
          maxLength={500}
        />
        <TouchableOpacity
          style={[styles.sendBtn, (!input.trim() || isTyping) && styles.sendBtnOff]}
          onPress={handleSend}
          disabled={!input.trim() || isTyping}
        >
          <Text style={styles.sendIcon}>✦</Text>
        </TouchableOpacity>
      </View>
    </KeyboardAvoidingView>
  );
}

const styles = StyleSheet.create({
  container: { flex: 1, backgroundColor: Colors.background },

  header: {
    flexDirection: 'row',
    alignItems: 'center',
    paddingHorizontal: 16,
    paddingTop: 56,
    paddingBottom: 16,
    backgroundColor: Colors.surface,
    borderBottomWidth: 1,
    borderBottomColor: Colors.border,
    gap: 12,
  },
  backBtn: {
    width: 38,
    height: 38,
    borderRadius: 12,
    backgroundColor: Colors.primaryLight,
    alignItems: 'center',
    justifyContent: 'center',
  },
  backArrow: { fontSize: 18, color: Colors.primary, fontWeight: '700' },
  headerLogo: {
    width: 38,
    height: 38,
    borderRadius: 19,
    backgroundColor: Colors.primary,
    alignItems: 'center',
    justifyContent: 'center',
  },
  headerLogoStar: { fontSize: 18, color: Colors.textInverse, fontWeight: '300' },

  aiInfo: { flex: 1, flexDirection: 'row', alignItems: 'center', gap: 12 },
  aiAvatarBox: {
    width: 40,
    height: 40,
    borderRadius: 20,
    backgroundColor: Colors.primary,
    alignItems: 'center',
    justifyContent: 'center',
    position: 'relative',
  },
  aiAvatarStar: { fontSize: 18, color: Colors.textInverse, fontWeight: '300' },
  onlineDot: {
    position: 'absolute',
    bottom: 1,
    right: 1,
    width: 10,
    height: 10,
    borderRadius: 5,
    backgroundColor: Colors.accent,
    borderWidth: 2,
    borderColor: Colors.surface,
  },
  aiBubbleAvatar: {
    width: 32,
    height: 32,
    borderRadius: 16,
    backgroundColor: Colors.primary,
    alignItems: 'center',
    justifyContent: 'center',
    alignSelf: 'flex-end',
    marginBottom: 2,
    flexShrink: 0,
  },
  aiBubbleAvatarStar: { fontSize: 14, color: Colors.textInverse, fontWeight: '300' },

  aiName: { fontSize: 15, fontWeight: '800', color: Colors.textPrimary },
  aiStatus: { fontSize: 11, color: Colors.accent, fontWeight: '500' },

  newChatBtn: {
    backgroundColor: Colors.primaryLight,
    borderRadius: 20,
    paddingHorizontal: 12,
    paddingVertical: 6,
    borderWidth: 1,
    borderColor: Colors.primary + '40',
  },
  newChatText: { color: Colors.primary, fontSize: 12, fontWeight: '700' },

  messageList: { padding: 16, gap: 16, paddingBottom: 8 },

  bubbleRow: { flexDirection: 'row', alignItems: 'flex-end', gap: 8 },
  bubbleRowUser: { flexDirection: 'row-reverse' },

  bubble: { maxWidth: '76%', borderRadius: 20, padding: 14, gap: 6 },
  userBubble: {
    backgroundColor: Colors.primary,
    borderBottomRightRadius: 4,
  },
  aiBubble: {
    backgroundColor: Colors.surface,
    borderBottomLeftRadius: 4,
    borderWidth: 1,
    borderColor: Colors.border,
  },
  bubbleText: { fontSize: 14, color: Colors.textPrimary, lineHeight: 22 },
  userBubbleText: { color: Colors.textInverse },
  timestamp: { fontSize: 10, color: Colors.textMuted, alignSelf: 'flex-end' },
  userTimestamp: { color: 'rgba(255,255,255,0.5)' },

  typingBubble: { flexDirection: 'row', alignItems: 'center', gap: 10 },
  typingText: { fontSize: 13, color: Colors.textSecondary, fontStyle: 'italic' },

  suggestions: { paddingHorizontal: 16, paddingBottom: 8 },
  suggestHeader: { flexDirection: 'row', alignItems: 'center', gap: 6, marginBottom: 10 },
  suggestStar: { fontSize: 12, color: Colors.primary },
  suggestLabel: {
    fontSize: 11,
    color: Colors.textMuted,
    fontWeight: '700',
    textTransform: 'uppercase',
    letterSpacing: 0.5,
  },
  chips: { flexDirection: 'row', flexWrap: 'wrap', gap: 8 },
  chip: {
    backgroundColor: Colors.surface,
    borderRadius: 20,
    paddingHorizontal: 14,
    paddingVertical: 8,
    borderWidth: 1,
    borderColor: Colors.border,
  },
  chipText: { fontSize: 12, color: Colors.textPrimary, fontWeight: '500' },

  inputBar: {
    flexDirection: 'row',
    alignItems: 'flex-end',
    padding: 12,
    gap: 10,
    backgroundColor: Colors.surface,
    borderTopWidth: 1,
    borderTopColor: Colors.border,
  },
  inputBarFocused: {
    borderTopColor: Colors.primary,
  },
  input: {
    flex: 1,
    backgroundColor: Colors.background,
    borderRadius: 24,
    borderWidth: 1.5,
    borderColor: Colors.border,
    paddingHorizontal: 16,
    paddingVertical: 12,
    fontSize: 14,
    color: Colors.textPrimary,
    maxHeight: 100,
  },
  sendBtn: {
    width: 46,
    height: 46,
    borderRadius: 23,
    backgroundColor: Colors.primary,
    alignItems: 'center',
    justifyContent: 'center',
    shadowColor: Colors.primary,
    shadowOffset: { width: 0, height: 2 },
    shadowOpacity: 0.4,
    shadowRadius: 6,
  },
  sendBtnOff: {
    backgroundColor: Colors.surfaceElevated,
    shadowOpacity: 0,
  },
  sendIcon: { color: Colors.textInverse, fontSize: 18, fontWeight: '300' },
});
