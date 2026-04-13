import React, { useState } from 'react';
import { View, Text, StyleSheet, TouchableOpacity } from 'react-native';
import { NativeStackScreenProps } from '@react-navigation/native-stack';
import { RootStackParamList } from '../navigation/AppNavigator';
import { Colors } from '../theme/colors';

type Props = NativeStackScreenProps<RootStackParamList, 'Onboarding'>;

const slides = [
  {
    emoji: '🦷',
    tag: 'Welcome',
    title: 'Your Oral Health\nCompanion',
    desc: 'Learn, track, and improve your dental hygiene with personalized AI-powered guidance.',
    bg: Colors.primary,
    iconBg: 'rgba(255,255,255,0.2)',
  },
  {
    emoji: '🤖',
    tag: 'AI Chatbot',
    title: 'Ask Anything,\nAnytime',
    desc: 'Get instant answers to any oral health question from our ORCare AI, powered by Google Gemini.',
    bg: Colors.secondary,
    iconBg: 'rgba(255,255,255,0.2)',
  },
  {
    emoji: '📚',
    tag: 'Learning',
    title: 'Explore &\nLearn',
    desc: '6 categories with 24+ modules covering daily hygiene, diseases, nutrition, and more.',
    bg: Colors.violet,
    iconBg: 'rgba(255,255,255,0.2)',
  },
  {
    emoji: '🔔',
    tag: 'Reminders',
    title: 'Build Habits\nThat Last',
    desc: 'Set smart reminders for brushing, flossing, and mouthwash to keep your smile healthy.',
    bg: Colors.accent,
    iconBg: 'rgba(255,255,255,0.2)',
  },
  {
    emoji: '🔍',
    tag: 'Symptom Check',
    title: 'Detect Early,\nStay Safe',
    desc: 'Check symptoms, explore oral diseases, and know exactly when to visit your dentist.',
    bg: Colors.rose,
    iconBg: 'rgba(255,255,255,0.2)',
  },
];

export default function OnboardingScreen({ navigation }: Props) {
  const [current, setCurrent] = useState(0);

  function handleNext() {
    if (current < slides.length - 1) {
      setCurrent(current + 1);
    } else {
      navigation.replace('MainTabs');
    }
  }

  function handleSkip() {
    navigation.replace('MainTabs');
  }

  const slide = slides[current];

  return (
    <View style={[styles.container, { backgroundColor: slide.bg }]}>
      {/* Top */}
      <View style={styles.topRow}>
        <View style={styles.tagPill}>
          <Text style={[styles.tagText, { color: slide.bg }]}>{slide.tag}</Text>
        </View>
        <TouchableOpacity onPress={handleSkip}>
          <Text style={styles.skipText}>Skip</Text>
        </TouchableOpacity>
      </View>

      {/* Illustration */}
      <View style={styles.illustrationArea}>
        <View style={styles.outerRing}>
          <View style={[styles.iconCircle, { backgroundColor: slide.iconBg }]}>
            <Text style={styles.slideEmoji}>{slide.emoji}</Text>
          </View>
        </View>

        {/* Floating decoration dots */}
        <View style={[styles.floatDot, { top: 40, right: 40, width: 12, height: 12, opacity: 0.4 }]} />
        <View style={[styles.floatDot, { top: 80, left: 30, width: 8, height: 8, opacity: 0.3 }]} />
        <View style={[styles.floatDot, { bottom: 40, right: 60, width: 16, height: 16, opacity: 0.25 }]} />
      </View>

      {/* Text */}
      <View style={styles.textArea}>
        <Text style={styles.slideTitle}>{slide.title}</Text>
        <Text style={styles.slideDesc}>{slide.desc}</Text>
      </View>

      {/* Bottom */}
      <View style={styles.bottomArea}>
        {/* Dots */}
        <View style={styles.dotsRow}>
          {slides.map((_, i) => (
            <View
              key={i}
              style={[styles.dot, i === current && styles.dotActive]}
            />
          ))}
        </View>

        {/* Next Button */}
        <TouchableOpacity style={styles.nextBtn} onPress={handleNext} activeOpacity={0.85}>
          <Text style={[styles.nextBtnText, { color: slide.bg }]}>
            {current === slides.length - 1 ? "Let's Get Started!" : 'Next →'}
          </Text>
        </TouchableOpacity>
      </View>
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    paddingTop: 60,
    paddingBottom: 48,
    paddingHorizontal: 28,
  },

  topRow: {
    flexDirection: 'row',
    justifyContent: 'space-between',
    alignItems: 'center',
    marginBottom: 24,
  },
  tagPill: {
    backgroundColor: 'rgba(255,255,255,0.9)',
    borderRadius: 20,
    paddingHorizontal: 14,
    paddingVertical: 6,
  },
  tagText: { fontSize: 12, fontWeight: '800', textTransform: 'uppercase', letterSpacing: 0.8 },
  skipText: { color: 'rgba(255,255,255,0.7)', fontSize: 15, fontWeight: '600' },

  illustrationArea: {
    flex: 1,
    alignItems: 'center',
    justifyContent: 'center',
    position: 'relative',
  },
  outerRing: {
    width: 200,
    height: 200,
    borderRadius: 100,
    borderWidth: 2,
    borderColor: 'rgba(255,255,255,0.2)',
    alignItems: 'center',
    justifyContent: 'center',
  },
  iconCircle: {
    width: 160,
    height: 160,
    borderRadius: 80,
    alignItems: 'center',
    justifyContent: 'center',
  },
  slideEmoji: { fontSize: 80 },
  floatDot: {
    position: 'absolute',
    borderRadius: 999,
    backgroundColor: 'rgba(255,255,255,1)',
  },

  textArea: { marginBottom: 32, gap: 12 },
  slideTitle: {
    fontSize: 32,
    fontWeight: '900',
    color: Colors.textInverse,
    lineHeight: 40,
    letterSpacing: -0.5,
  },
  slideDesc: {
    fontSize: 15,
    color: 'rgba(255,255,255,0.8)',
    lineHeight: 24,
  },

  bottomArea: { gap: 20 },
  dotsRow: { flexDirection: 'row', gap: 8 },
  dot: {
    width: 8,
    height: 8,
    borderRadius: 4,
    backgroundColor: 'rgba(255,255,255,0.35)',
  },
  dotActive: {
    width: 28,
    height: 8,
    borderRadius: 4,
    backgroundColor: Colors.textInverse,
  },

  nextBtn: {
    backgroundColor: Colors.textInverse,
    borderRadius: 16,
    padding: 18,
    alignItems: 'center',
  },
  nextBtnText: { fontSize: 16, fontWeight: '800' },
});
