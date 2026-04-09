const { GoogleGenerativeAI } = require('@google/generative-ai');
const fs = require('fs');
const path = require('path');

// Load clinical knowledge base
const kbPath = path.join(__dirname, '../../knowledge_base.md');
let clinicalKnowledge = "";
try {
    clinicalKnowledge = fs.readFileSync(kbPath, 'utf8');
} catch (error) {
    console.warn("Clinical Knowledge Base not found. Proceeding with limited context.");
}

const genAI = new GoogleGenerativeAI(process.env.GEMINI_API_KEY);
const model = genAI.getGenerativeModel({
    model: "gemini-1.5-flash",
    systemInstruction: `You are ORCare AI, a professional dental assistant. Your goal is to provide accurate oral health guidance based ON ONLY the provided content.

CLINICAL KNOWLEDGE BASE:
${clinicalKnowledge}

GUIDELINES:
1. Always be polite and professional.
2. If a user describes symptoms, cross-reference with the Symptoms and Diseases tables in the knowledge base.
3. If a symptom is severe (bleeding, swelling, loose teeth, or pain lasting >2 days), ALWAYS advise seeing a dentist.
4. If a question is NOT related to dental or oral health, politely decline to answer.
5. Provide actionable advice from the "What to do" or "Daily Tips" sections.
6. Mention specifically when a user should see a dentist based on clinical criteria.
7. Keep responses concise and focused. Use formatting (bullet points) for clarity.`
});

const Chat = require('../models/Chat');

const chatController = {
    // @desc    Get AI Response
    // @route   POST /api/chat/chat
    // @access  Public (or Private if token provided)
    getChatResponse: async (req, res) => {
        try {
            const { message, history } = req.body;

            if (!message) {
                return res.status(400).json({ message: "Message is required" });
            }

            const chat = model.startChat({
                history: history || [],
            });

            const result = await chat.sendMessage(message);
            const response = await result.response;
            const text = response.text();

            res.json({ text });
        } catch (error) {
            console.error("AI Error:", error);
            res.status(500).json({ message: "Error communicating with ORCare AI" });
        }
    },

    // @desc    Save chat messages to database
    // @route   POST /api/chat/save
    // @access  Private
    saveChatHistory: async (req, res) => {
        try {
            const { sessionId, title, messages } = req.body;
            const userId = req.user._id;

            let chat = await Chat.findOne({ userId, sessionId });

            if (chat) {
                // If appending messages
                if (messages && messages.length > 0) {
                    chat.messages.push(...messages);
                }
                if (title) chat.title = title;
                await chat.save();
            } else {
                // Create new session
                chat = new Chat({
                    userId,
                    sessionId,
                    title: title || 'New Chat',
                    messages: messages || []
                });
                await chat.save();
            }

            res.status(201).json({ success: true, chat });
        } catch (error) {
            res.status(500).json({ message: error.message });
        }
    },

    // @desc    Get user chat history
    // @route   GET /api/chat/history
    // @access  Private
    getUserChats: async (req, res) => {
        try {
            const chats = await Chat.find({ userId: req.user._id }).sort({ updatedAt: -1 });
            res.json(chats);
        } catch (error) {
            res.status(500).json({ message: error.message });
        }
    }
};

module.exports = chatController;
