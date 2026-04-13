import React, { useState } from 'react';
import { View, Text, StyleSheet, ScrollView, TouchableOpacity } from 'react-native';
import { NativeStackScreenProps } from '@react-navigation/native-stack';
import { RootStackParamList } from '../navigation/AppNavigator';
import { Colors } from '../theme/colors';
import { learningCategories } from '../data/learningData';

type Props = NativeStackScreenProps<RootStackParamList, 'ModuleDetail'>;

type Phase = 'lessons' | 'quiz' | 'result';

export default function ModuleDetailScreen({ navigation, route }: Props) {
  const { moduleId, categoryId } = route.params;
  const category = learningCategories.find((c) => c.id === categoryId);
  const module = category?.modules.find((m) => m.id === moduleId);

  const [lessonIndex, setLessonIndex] = useState(0);
  const [phase, setPhase] = useState<Phase>('lessons');
  const [quizIndex, setQuizIndex] = useState(0);
  const [selected, setSelected] = useState<number | null>(null);
  const [score, setScore] = useState(0);

  if (!module || !category) {
    return (
      <View style={styles.container}>
        <TouchableOpacity style={styles.backBtn} onPress={() => navigation.goBack()}>
          <Text style={styles.backArrow}>←</Text>
        </TouchableOpacity>
        <View style={styles.emptyState}>
          <Text style={styles.emptyEmoji}>📖</Text>
          <Text style={styles.emptyText}>Module not found.</Text>
        </View>
      </View>
    );
  }

  function nextLesson() {
    if (lessonIndex < module!.lessons.length - 1) {
      setLessonIndex(lessonIndex + 1);
    } else {
      setPhase('quiz');
      setQuizIndex(0);
    }
  }

  function handleAnswer(optionIndex: number) {
    if (selected !== null) return;
    setSelected(optionIndex);
    if (optionIndex === module!.quiz[quizIndex].correctIndex) {
      setScore((s) => s + 1);
    }
  }

  function nextQuestion() {
    if (quizIndex < module!.quiz.length - 1) {
      setQuizIndex(quizIndex + 1);
      setSelected(null);
    } else {
      setPhase('result');
    }
  }

  // RESULT SCREEN
  if (phase === 'result') {
    const passed = score >= Math.ceil(module.quiz.length / 2);
    return (
      <View style={[styles.container, styles.resultContainer]}>
        <View style={[styles.resultBadge, { backgroundColor: passed ? Colors.secondaryLight : Colors.amberLight }]}>
          <Text style={styles.resultEmoji}>{passed ? '🎉' : '📖'}</Text>
        </View>
        <Text style={styles.resultTitle}>{passed ? 'Well Done!' : 'Keep Practicing!'}</Text>
        <Text style={styles.resultSub}>{passed ? 'You passed the quiz!' : 'Review the lessons and try again'}</Text>

        <View style={styles.scoreCard}>
          <View style={styles.scoreItem}>
            <Text style={styles.scoreVal}>{score}</Text>
            <Text style={styles.scoreLabel}>Correct</Text>
          </View>
          <View style={styles.scoreDivider} />
          <View style={styles.scoreItem}>
            <Text style={styles.scoreVal}>{module.quiz.length - score}</Text>
            <Text style={styles.scoreLabel}>Wrong</Text>
          </View>
          <View style={styles.scoreDivider} />
          <View style={styles.scoreItem}>
            <Text style={[styles.scoreVal, { color: Colors.amber }]}>{passed ? module.points : 0}</Text>
            <Text style={styles.scoreLabel}>Points</Text>
          </View>
        </View>

        {passed && (
          <View style={styles.successBanner}>
            <Text style={styles.successText}>⭐ +{module.points} points earned!</Text>
          </View>
        )}

        <TouchableOpacity style={styles.doneBtn} onPress={() => navigation.goBack()}>
          <Text style={styles.doneBtnText}>Back to Modules</Text>
        </TouchableOpacity>

        {!passed && (
          <TouchableOpacity
            style={styles.retryBtn}
            onPress={() => {
              setPhase('quiz');
              setQuizIndex(0);
              setSelected(null);
              setScore(0);
            }}
          >
            <Text style={styles.retryBtnText}>🔄 Retry Quiz</Text>
          </TouchableOpacity>
        )}
      </View>
    );
  }

  // QUIZ SCREEN
  if (phase === 'quiz') {
    const q = module.quiz[quizIndex];
    const progressPct = ((quizIndex + 1) / module.quiz.length) * 100;

    return (
      <View style={styles.container}>
        {/* Header */}
        <View style={[styles.header, { backgroundColor: category.bgColor }]}>
          <TouchableOpacity
            style={[styles.backBtn, { backgroundColor: category.color + '20' }]}
            onPress={() => navigation.goBack()}
          >
            <Text style={[styles.backArrow, { color: category.color }]}>←</Text>
          </TouchableOpacity>

          <View style={styles.headerInfo}>
            <View style={styles.quizBadge}>
              <Text style={[styles.quizBadgeText, { color: category.color }]}>Quiz Time! 🧠</Text>
            </View>
            <Text style={styles.headerMeta}>Question {quizIndex + 1} of {module.quiz.length}</Text>
          </View>

          <View style={styles.progressBar}>
            <View style={[styles.progressFill, { width: `${progressPct}%` as any, backgroundColor: category.color }]} />
          </View>
        </View>

        <ScrollView contentContainerStyle={styles.quizContent} showsVerticalScrollIndicator={false}>
          {/* Question */}
          <View style={styles.questionCard}>
            <Text style={styles.questionNumber}>Q{quizIndex + 1}</Text>
            <Text style={styles.questionText}>{q.question}</Text>
          </View>

          {/* Options */}
          <View style={styles.optionsList}>
            {q.options.map((option, i) => {
              let bg = Colors.surface;
              let borderColor = Colors.border;
              let textColor = Colors.textPrimary;
              let icon = '';

              if (selected !== null) {
                if (i === q.correctIndex) {
                  bg = Colors.secondaryLight;
                  borderColor = Colors.secondary;
                  textColor = Colors.secondary;
                  icon = '✓';
                } else if (i === selected && i !== q.correctIndex) {
                  bg = Colors.errorLight;
                  borderColor = Colors.error;
                  textColor = Colors.error;
                  icon = '✗';
                }
              }

              return (
                <TouchableOpacity
                  key={i}
                  style={[styles.optionCard, { backgroundColor: bg, borderColor }]}
                  onPress={() => handleAnswer(i)}
                  disabled={selected !== null}
                  activeOpacity={0.8}
                >
                  <View style={[styles.optionIndex, { borderColor }]}>
                    <Text style={[styles.optionIndexText, { color: textColor }]}>
                      {icon || String.fromCharCode(65 + i)}
                    </Text>
                  </View>
                  <Text style={[styles.optionText, { color: textColor }]}>{option}</Text>
                </TouchableOpacity>
              );
            })}
          </View>

          {selected !== null && (
            <View style={[
              styles.feedbackCard,
              { backgroundColor: selected === q.correctIndex ? Colors.secondaryLight : Colors.errorLight }
            ]}>
              <Text style={[
                styles.feedbackText,
                { color: selected === q.correctIndex ? Colors.secondary : Colors.error }
              ]}>
                {selected === q.correctIndex ? '🎉 Correct! Well done!' : `❌ The correct answer was: "${q.options[q.correctIndex]}"`}
              </Text>
            </View>
          )}

          {selected !== null && (
            <TouchableOpacity style={styles.nextBtn} onPress={nextQuestion}>
              <Text style={styles.nextBtnText}>
                {quizIndex < module.quiz.length - 1 ? 'Next Question →' : 'See Results 🏆'}
              </Text>
            </TouchableOpacity>
          )}

          <View style={{ height: 24 }} />
        </ScrollView>
      </View>
    );
  }

  // LESSON SCREEN
  const lesson = module.lessons[lessonIndex];
  const progressPct = ((lessonIndex + 1) / module.lessons.length) * 100;

  return (
    <View style={styles.container}>
      {/* Header */}
      <View style={[styles.header, { backgroundColor: category.bgColor }]}>
        <TouchableOpacity
          style={[styles.backBtn, { backgroundColor: category.color + '20' }]}
          onPress={() => navigation.goBack()}
        >
          <Text style={[styles.backArrow, { color: category.color }]}>←</Text>
        </TouchableOpacity>

        <View style={styles.headerInfo}>
          <Text style={[styles.headerTitle, { color: category.color }]}>{module.title}</Text>
          <Text style={styles.headerMeta}>Lesson {lessonIndex + 1} of {module.lessons.length}</Text>
        </View>

        <View style={styles.progressBar}>
          <View style={[styles.progressFill, { width: `${progressPct}%` as any, backgroundColor: category.color }]} />
        </View>
      </View>

      <ScrollView contentContainerStyle={styles.lessonContent} showsVerticalScrollIndicator={false}>
        {/* Lesson Title */}
        <View style={[styles.lessonTitleCard, { backgroundColor: category.bgColor }]}>
          <Text style={[styles.lessonTitle, { color: category.color }]}>{lesson.title}</Text>
        </View>

        {/* Lesson Body */}
        <View style={styles.lessonBody}>
          <Text style={styles.lessonText}>{lesson.content}</Text>
        </View>

        {/* Up Next */}
        {lessonIndex < module.lessons.length - 1 && (
          <View style={styles.upNextCard}>
            <Text style={styles.upNextLabel}>UP NEXT</Text>
            <Text style={styles.upNextTitle}>{module.lessons[lessonIndex + 1].title}</Text>
          </View>
        )}

        {lessonIndex === module.lessons.length - 1 && (
          <View style={[styles.upNextCard, { backgroundColor: Colors.amberLight }]}>
            <Text style={[styles.upNextLabel, { color: Colors.amber }]}>READY?</Text>
            <Text style={[styles.upNextTitle, { color: Colors.textPrimary }]}>Quiz time — test your knowledge!</Text>
          </View>
        )}

        <View style={{ height: 100 }} />
      </ScrollView>

      {/* Footer Navigation */}
      <View style={styles.lessonFooter}>
        {lessonIndex > 0 && (
          <TouchableOpacity
            style={styles.prevBtn}
            onPress={() => setLessonIndex(lessonIndex - 1)}
          >
            <Text style={styles.prevBtnText}>← Prev</Text>
          </TouchableOpacity>
        )}
        <TouchableOpacity
          style={[styles.nextLessonBtn, { backgroundColor: category.color }]}
          onPress={nextLesson}
        >
          <Text style={styles.nextLessonBtnText}>
            {lessonIndex < module.lessons.length - 1 ? 'Next Lesson →' : 'Start Quiz ❓'}
          </Text>
        </TouchableOpacity>
      </View>
    </View>
  );
}

const styles = StyleSheet.create({
  container: { flex: 1, backgroundColor: Colors.background },

  backBtn: {
    width: 38,
    height: 38,
    borderRadius: 12,
    alignItems: 'center',
    justifyContent: 'center',
    marginBottom: 12,
    alignSelf: 'flex-start',
  },
  backArrow: { fontSize: 18, fontWeight: '700' },

  header: {
    paddingTop: 56,
    paddingHorizontal: 20,
    paddingBottom: 16,
  },
  headerInfo: { marginBottom: 12, gap: 2 },
  headerTitle: { fontSize: 18, fontWeight: '800' },
  headerMeta: { fontSize: 12, color: Colors.textSecondary },
  quizBadge: {
    alignSelf: 'flex-start',
    backgroundColor: 'rgba(0,0,0,0.08)',
    borderRadius: 20,
    paddingHorizontal: 12,
    paddingVertical: 4,
    marginBottom: 4,
  },
  quizBadgeText: { fontSize: 14, fontWeight: '800' },
  progressBar: { height: 6, backgroundColor: 'rgba(0,0,0,0.1)', borderRadius: 3, overflow: 'hidden' },
  progressFill: { height: 6, borderRadius: 3 },

  // Lesson
  lessonContent: { padding: 20, gap: 16 },
  lessonTitleCard: {
    borderRadius: 16,
    padding: 16,
  },
  lessonTitle: { fontSize: 18, fontWeight: '800', lineHeight: 26 },
  lessonBody: {
    backgroundColor: Colors.surface,
    borderRadius: 16,
    padding: 20,
    borderWidth: 1,
    borderColor: Colors.border,
  },
  lessonText: { fontSize: 15, color: Colors.textPrimary, lineHeight: 26 },
  upNextCard: {
    backgroundColor: Colors.primaryLight,
    borderRadius: 14,
    padding: 14,
    gap: 4,
  },
  upNextLabel: {
    fontSize: 10,
    fontWeight: '800',
    color: Colors.primary,
    textTransform: 'uppercase',
    letterSpacing: 0.8,
  },
  upNextTitle: { fontSize: 14, fontWeight: '600', color: Colors.primary },

  lessonFooter: {
    flexDirection: 'row',
    padding: 16,
    gap: 12,
    backgroundColor: Colors.surface,
    borderTopWidth: 1,
    borderTopColor: Colors.border,
  },
  prevBtn: {
    paddingHorizontal: 20,
    paddingVertical: 14,
    borderRadius: 14,
    backgroundColor: Colors.surfaceElevated,
    borderWidth: 1,
    borderColor: Colors.border,
  },
  prevBtnText: { color: Colors.textSecondary, fontWeight: '700', fontSize: 14 },
  nextLessonBtn: { flex: 1, borderRadius: 14, padding: 14, alignItems: 'center' },
  nextLessonBtnText: { color: Colors.textInverse, fontWeight: '800', fontSize: 15 },

  // Quiz
  quizContent: { padding: 20, gap: 16 },
  questionCard: {
    backgroundColor: Colors.surface,
    borderRadius: 18,
    padding: 20,
    borderWidth: 1,
    borderColor: Colors.border,
    gap: 10,
  },
  questionNumber: {
    fontSize: 11,
    fontWeight: '800',
    color: Colors.primary,
    textTransform: 'uppercase',
    letterSpacing: 0.8,
  },
  questionText: { fontSize: 17, fontWeight: '700', color: Colors.textPrimary, lineHeight: 26 },

  optionsList: { gap: 10 },
  optionCard: {
    flexDirection: 'row',
    alignItems: 'center',
    borderRadius: 14,
    borderWidth: 2,
    padding: 14,
    gap: 12,
  },
  optionIndex: {
    width: 32,
    height: 32,
    borderRadius: 16,
    borderWidth: 2,
    alignItems: 'center',
    justifyContent: 'center',
    flexShrink: 0,
  },
  optionIndexText: { fontSize: 13, fontWeight: '800' },
  optionText: { fontSize: 14, fontWeight: '500', flex: 1, lineHeight: 20 },

  feedbackCard: {
    borderRadius: 14,
    padding: 14,
  },
  feedbackText: { fontSize: 14, fontWeight: '600', lineHeight: 20 },

  nextBtn: {
    backgroundColor: Colors.primary,
    borderRadius: 14,
    padding: 16,
    alignItems: 'center',
    shadowColor: Colors.primary,
    shadowOffset: { width: 0, height: 4 },
    shadowOpacity: 0.3,
    shadowRadius: 8,
  },
  nextBtnText: { color: Colors.textInverse, fontSize: 16, fontWeight: '800' },

  // Result
  resultContainer: {
    alignItems: 'center',
    justifyContent: 'center',
    padding: 32,
    gap: 16,
  },
  resultBadge: {
    width: 120,
    height: 120,
    borderRadius: 60,
    alignItems: 'center',
    justifyContent: 'center',
    marginBottom: 8,
  },
  resultEmoji: { fontSize: 64 },
  resultTitle: { fontSize: 28, fontWeight: '900', color: Colors.textPrimary },
  resultSub: { fontSize: 14, color: Colors.textSecondary, textAlign: 'center' },

  scoreCard: {
    flexDirection: 'row',
    backgroundColor: Colors.surface,
    borderRadius: 18,
    padding: 20,
    borderWidth: 1,
    borderColor: Colors.border,
    gap: 0,
    width: '100%',
  },
  scoreItem: { flex: 1, alignItems: 'center', gap: 4 },
  scoreVal: { fontSize: 28, fontWeight: '900', color: Colors.textPrimary },
  scoreLabel: { fontSize: 12, color: Colors.textMuted, fontWeight: '600' },
  scoreDivider: { width: 1, backgroundColor: Colors.border, marginVertical: 8 },

  successBanner: {
    backgroundColor: Colors.amberLight,
    borderRadius: 12,
    paddingHorizontal: 20,
    paddingVertical: 10,
    width: '100%',
    alignItems: 'center',
  },
  successText: { fontSize: 15, color: Colors.amber, fontWeight: '700' },

  doneBtn: {
    backgroundColor: Colors.primary,
    borderRadius: 14,
    padding: 16,
    width: '100%',
    alignItems: 'center',
    shadowColor: Colors.primary,
    shadowOffset: { width: 0, height: 4 },
    shadowOpacity: 0.3,
    shadowRadius: 8,
  },
  doneBtnText: { color: Colors.textInverse, fontSize: 16, fontWeight: '800' },

  retryBtn: {
    borderWidth: 2,
    borderColor: Colors.border,
    borderRadius: 14,
    padding: 14,
    width: '100%',
    alignItems: 'center',
  },
  retryBtnText: { color: Colors.textSecondary, fontSize: 15, fontWeight: '700' },

  emptyState: { flex: 1, alignItems: 'center', justifyContent: 'center', gap: 16 },
  emptyEmoji: { fontSize: 56 },
  emptyText: { fontSize: 16, color: Colors.textSecondary },
});
