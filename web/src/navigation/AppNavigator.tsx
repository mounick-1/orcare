import React from 'react';
import { NavigationContainer } from '@react-navigation/native';
import { createNativeStackNavigator } from '@react-navigation/native-stack';
import { createBottomTabNavigator } from '@react-navigation/bottom-tabs';
import { View, Text, StyleSheet } from 'react-native';
import { Home, Bot, BookOpen, Microscope, User } from 'lucide-react-native';
import { useAuth } from '../context/AuthContext';
import { Colors } from '../theme/colors';

import SplashScreen from '../screens/SplashScreen';
import SignInScreen from '../screens/SignInScreen';
import SignUpScreen from '../screens/SignUpScreen';
import OtpVerificationScreen from '../screens/OtpVerificationScreen';
import ForgotPasswordScreen from '../screens/ForgotPasswordScreen';
import OnboardingScreen from '../screens/OnboardingScreen';
import HomeScreen from '../screens/HomeScreen';
import LearningCenterScreen from '../screens/LearningCenterScreen';
import OralDiseaseScreen from '../screens/OralDiseaseScreen';
import ProfileScreen from '../screens/ProfileScreen';
import SymptomCheckerScreen from '../screens/SymptomCheckerScreen';
import SymptomDetailScreen from '../screens/SymptomDetailScreen';
import ChatbotScreen from '../screens/ChatbotScreen';
import DiseaseDetailScreen from '../screens/DiseaseDetailScreen';
import LearningCategoryScreen from '../screens/LearningCategoryScreen';
import ModuleDetailScreen from '../screens/ModuleDetailScreen';
import RemindersScreen from '../screens/RemindersScreen';
import DailyTipsScreen from '../screens/DailyTipsScreen';
import EditProfileScreen from '../screens/EditProfileScreen';
import HelpFeedbackScreen from '../screens/HelpFeedbackScreen';
import PrivacyPolicyScreen from '../screens/PrivacyPolicyScreen';
import PrivacySecurityScreen from '../screens/PrivacySecurityScreen';
import ResetPasswordScreen from '../screens/ResetPasswordScreen';
import DeleteAccountScreen from '../screens/DeleteAccountScreen';

export type RootStackParamList = {
  Splash: undefined;
  SignIn: undefined;
  SignUp: undefined;
  OtpVerification: { email: string; type: 'register' | 'forgot' };
  ForgotPassword: undefined;
  Onboarding: undefined;
  MainTabs: undefined;
  SymptomChecker: undefined;
  SymptomDetail: { symptomName: string };
  Chatbot: { symptomName?: string };
  DiseaseDetail: { diseaseId: string };
  LearningCategory: { categoryId: string };
  ModuleDetail: { moduleId: string; categoryId: string };
  Reminders: undefined;
  DailyTips: undefined;
  EditProfile: undefined;
  HelpFeedback: undefined;
  PrivacyPolicy: undefined;
  PrivacySecurity: undefined;
  ResetPassword: { email: string; otp: string };
  DeleteAccount: undefined;
};

export type MainTabParamList = {
  Home: undefined;
  ChatTab: undefined;
  LearningCenter: undefined;
  OralDisease: undefined;
  Profile: undefined;
};

const Stack = createNativeStackNavigator<RootStackParamList>();
const Tab = createBottomTabNavigator<MainTabParamList>();

function TabIcon({
  Icon,
  label,
  focused,
}: {
  Icon: React.ComponentType<{ size: number; color: string; strokeWidth: number }>;
  label: string;
  focused: boolean;
}) {
  return (
    <View style={tabStyles.iconWrap}>
      {focused && <View style={tabStyles.activePill} />}
      <View style={[tabStyles.iconBox, focused && tabStyles.iconBoxActive]}>
        <Icon
          size={20}
          color={focused ? Colors.primary : Colors.textMuted}
          strokeWidth={focused ? 2.5 : 1.75}
        />
      </View>
      <Text style={[tabStyles.label, focused && tabStyles.labelActive]}>{label}</Text>
    </View>
  );
}

const tabStyles = StyleSheet.create({
  iconWrap: { alignItems: 'center', justifyContent: 'center', paddingTop: 6, width: 64 },
  activePill: {
    position: 'absolute',
    top: 0,
    width: 32,
    height: 3,
    borderRadius: 2,
    backgroundColor: Colors.primary,
  },
  iconBox: {
    width: 40,
    height: 34,
    borderRadius: 10,
    alignItems: 'center',
    justifyContent: 'center',
  },
  iconBoxActive: { backgroundColor: Colors.primaryLight },
  label: { fontSize: 10, color: Colors.textMuted, marginTop: 2, fontFamily: 'Inter_500Medium' },
  labelActive: { color: Colors.primary, fontFamily: 'Inter_600SemiBold' },
});

function ChatTabWrapper() {
  return <ChatbotScreen navigation={undefined as any} route={{ params: {} } as any} />;
}

function MainTabs() {
  return (
    <Tab.Navigator
      screenOptions={{
        headerShown: false,
        tabBarStyle: {
          backgroundColor: Colors.surface,
          borderTopWidth: 1,
          borderTopColor: Colors.border,
          height: 72,
          paddingBottom: 8,
          paddingTop: 0,
          elevation: 8,
          shadowColor: Colors.shadowNeutral,
          shadowOffset: { width: 0, height: -2 },
          shadowOpacity: 1,
          shadowRadius: 8,
        },
        tabBarShowLabel: false,
      }}
    >
      <Tab.Screen name="Home" component={HomeScreen}
        options={{ tabBarIcon: ({ focused }) => <TabIcon Icon={Home} label="Home" focused={focused} /> }} />
      <Tab.Screen name="ChatTab" component={ChatTabWrapper}
        options={{ tabBarIcon: ({ focused }) => <TabIcon Icon={Bot} label="AI Chat" focused={focused} /> }} />
      <Tab.Screen name="LearningCenter" component={LearningCenterScreen}
        options={{ tabBarIcon: ({ focused }) => <TabIcon Icon={BookOpen} label="Learn" focused={focused} /> }} />
      <Tab.Screen name="OralDisease" component={OralDiseaseScreen}
        options={{ tabBarIcon: ({ focused }) => <TabIcon Icon={Microscope} label="Diseases" focused={focused} /> }} />
      <Tab.Screen name="Profile" component={ProfileScreen}
        options={{ tabBarIcon: ({ focused }) => <TabIcon Icon={User} label="Profile" focused={focused} /> }} />
    </Tab.Navigator>
  );
}

export default function AppNavigator() {
  const { isLoading } = useAuth();

  if (isLoading) {
    return (
      <View style={{ flex: 1, alignItems: 'center', justifyContent: 'center', backgroundColor: Colors.background }}>
        <View style={{ width: 72, height: 72, borderRadius: 36, backgroundColor: Colors.primary, alignItems: 'center', justifyContent: 'center' }}>
          <Text style={{ fontSize: 32, color: '#FFFFFF' }}>✦</Text>
        </View>
        <Text style={{ color: Colors.textPrimary, marginTop: 16, fontSize: 22, fontFamily: 'Inter_800ExtraBold', letterSpacing: 1 }}>ORCare</Text>
      </View>
    );
  }

  return (
    <NavigationContainer>
      <Stack.Navigator screenOptions={{ headerShown: false, animation: 'none' }} initialRouteName="Splash">
        <Stack.Screen name="Splash" component={SplashScreen} />
        <Stack.Screen name="SignIn" component={SignInScreen} />
        <Stack.Screen name="SignUp" component={SignUpScreen} />
        <Stack.Screen name="OtpVerification" component={OtpVerificationScreen} />
        <Stack.Screen name="ForgotPassword" component={ForgotPasswordScreen} />
        <Stack.Screen name="Onboarding" component={OnboardingScreen} />
        <Stack.Screen name="MainTabs" component={MainTabs} />
        <Stack.Screen name="SymptomChecker" component={SymptomCheckerScreen} />
        <Stack.Screen name="SymptomDetail" component={SymptomDetailScreen} />
        <Stack.Screen name="Chatbot" component={ChatbotScreen} />
        <Stack.Screen name="DiseaseDetail" component={DiseaseDetailScreen} />
        <Stack.Screen name="LearningCategory" component={LearningCategoryScreen} />
        <Stack.Screen name="ModuleDetail" component={ModuleDetailScreen} />
        <Stack.Screen name="Reminders" component={RemindersScreen} />
        <Stack.Screen name="DailyTips" component={DailyTipsScreen} />
        <Stack.Screen name="EditProfile" component={EditProfileScreen} />
        <Stack.Screen name="HelpFeedback" component={HelpFeedbackScreen} />
        <Stack.Screen name="PrivacyPolicy" component={PrivacyPolicyScreen} />
        <Stack.Screen name="PrivacySecurity" component={PrivacySecurityScreen} />
        <Stack.Screen name="ResetPassword" component={ResetPasswordScreen} />
        <Stack.Screen name="DeleteAccount" component={DeleteAccountScreen} />
      </Stack.Navigator>
    </NavigationContainer>
  );
}
