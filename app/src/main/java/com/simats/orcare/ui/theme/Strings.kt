package com.simats.orcare.ui.theme

import androidx.compose.runtime.staticCompositionLocalOf

interface ORCareStrings {
    // Auth & Common
    val appName: String
    val email: String
    val password: String
    val login: String
    val signUp: String
    val forgotPassword: String
    val welcomeBack: String
    val createAccount: String
    val dontHaveAccount: String
    val alreadyHaveAccount: String
    val joinOrCare: String
    val signInToContinue: String
    val enterEmailToReset: String
    val sendOtp: String
    val backToLogin: String
    val verification: String
    val verified: String
    val otpVerification: String
    
    // Home
    val hello: String
    val homeQuestion: String
    val aiAssistant: String
    val aiAssistantSubtitle: String
    val quickActions: String
    val complaintChecker: String
    val learningCenter: String
    val dailyTips: String
    val commonSymptoms: String
    val dailyBrushingTip: String
    val oralDiseases: String
    val commonOralConditions: String
    val oralDiseaseSubtitle: String
    
    // Profile
    val profile: String
    val logout: String
    val members: String
    val settings: String
    val reminders: String
    val privacySecurity: String
    val privacyPolicy: String
    val helpFeedback: String
    val language: String
    val saveChanges: String
    val deleteAccount: String
    val editProfile: String
    val emailAddress: String
    val location: String
    
    // Navigation
    val navHome: String
    val navChatbot: String
    val navEducation: String
    val navProfile: String

    // Registration Extra
    val fullName: String
    val ageLabel: String
    val genderLabel: String
    val confirmPassword: String
    val selectGender: String
    
    // Onboarding
    val skip: String
    val continueLabel: String
    val getStarted: String
    val onboarding1Title: String
    val onboarding1Desc: String
    val onboarding2Title: String
    val onboarding2Desc: String
    val onboarding3Title: String
    val onboarding3Desc: String
    val onboarding4Title: String
    val onboarding4Desc: String
    val onboarding5Title: String
    val onboarding5Desc: String
    
    val chooseLanguage: String

    // Chatbot
    val chatHistory: String
    val startNewChat: String
    val askAnything: String
    val orcareAI: String
    
    // Symptoms
    val checkDentalComplaints: String
    val selectSymptomDesc: String
    
    // Education
    val learningPath: String
    val featured: String
    val startLesson: String
    val learningHeader: String
    val learningSubtitle: String
    val professionalModules: String
}

class EnglishStrings : ORCareStrings {
    override val appName = "ORCare"
    override val email = "Email Address"
    override val password = "Password"
    override val login = "Sign In"
    override val signUp = "Sign Up"
    override val forgotPassword = "Forgot Password?"
    override val welcomeBack = "Welcome Back"
    override val createAccount = "Create Account"
    override val dontHaveAccount = "Don't have an account? "
    override val alreadyHaveAccount = "Already have an account? "
    override val joinOrCare = "Join ORCare for better oral health"
    override val signInToContinue = "Sign in to continue your oral health journey"
    override val enterEmailToReset = "Enter your email to reset password"
    override val sendOtp = "Send OTP"
    override val backToLogin = "Back to Login"
    override val verification = "Verification"
    override val verified = "Verified"
    override val otpVerification = "OTP Verification"
    
    override val hello = "Hello"
    override val homeQuestion = "What oral health disease are you here to study today?"
    override val aiAssistant = "AI Health Assistant"
    override val aiAssistantSubtitle = "Your personal oral care expert"
    override val quickActions = "Quick Actions"
    override val complaintChecker = "Complaint Checker"
    override val learningCenter = "Learning Center"
    override val dailyTips = "Daily Tips"
    override val commonSymptoms = "Common Symptoms"
    override val dailyBrushingTip = "Daily Brushing Tip"
    override val oralDiseases = "Oral Diseases"
    override val commonOralConditions = "Common Oral Conditions"
    override val oralDiseaseSubtitle = "Learn about common oral health issues, what signs to look for, and when to see a dentist."
    
    override val profile = "Profile"
    override val logout = "Logout"
    override val members = "OrCare Member"
    override val settings = "Settings"
    override val reminders = "Reminders"
    override val privacySecurity = "Privacy & Data Security"
    override val privacyPolicy = "Privacy Policy"
    override val helpFeedback = "Help / Feedback"
    override val language = "Language"
    override val saveChanges = "Save Changes"
    override val deleteAccount = "Delete Account"
    override val editProfile = "Edit Profile"
    override val emailAddress = "Email Address"
    override val location = "Location"
    
    override val navHome = "Home"
    override val navChatbot = "Assistant"
    override val navEducation = "Learn"
    override val navProfile = "Profile"

    override val fullName = "Full Name"
    override val ageLabel = "Age"
    override val genderLabel = "Gender"
    override val confirmPassword = "Confirm Password"
    override val selectGender = "Select Gender"
    
    override val skip = "Skip"
    override val continueLabel = "Continue"
    override val getStarted = "Get Started"
    override val onboarding1Title = "AI-Powered Dental Chatbot"
    override val onboarding1Desc = "Get instant answers to your oral health questions. Our AI chatbot provides 24/7 guidance in your language."
    override val onboarding2Title = "Smart Symptom Checker"
    override val onboarding2Desc = "Check your dental symptoms and receive personalized advice on when to see a dentist."
    override val onboarding3Title = "Learning Modules & Quizzes"
    override val onboarding3Desc = "Access educational content with videos, articles, and interactive quizzes designed for rural communities."
    override val onboarding4Title = "Multi-Language Support"
    override val onboarding4Desc = "Use the app in English, Tamil, Telugu, or Hindi. Switch languages anytime from settings."
    override val onboarding5Title = "Daily Tips & Reminders"
    override val onboarding5Desc = "Receive personalized oral health tips and set reminders for brushing and dental checkups."
    
    override val chooseLanguage = "Choose Language"
    override val chatHistory = "Chat History"
    override val startNewChat = "Start New Chat"
    override val askAnything = "Ask anything..."
    override val orcareAI = "ORCare AI"
    
    override val checkDentalComplaints = "Check your dental complaints"
    override val selectSymptomDesc = "Select a symptom to learn more about possible causes and home care tips"
    
    override val learningPath = "Learning Path"
    override val featured = "FEATURED"
    override val startLesson = "Start Lesson"
    override val learningHeader = "Your Path to a Brighter Smile"
    override val learningSubtitle = "Master your dental health with professional guidance."
    override val professionalModules = "Professional Modules"
}

class TamilStrings : ORCareStrings {
    override val appName = "ORCare"
    override val email = "மின்னஞ்சல் முகவரி"
    override val password = "கடவுச்சொல்"
    override val login = "உள்நுழைய"
    override val signUp = "பதிவு செய்"
    override val forgotPassword = "கடவுச்சொல் மறந்ததா?"
    override val welcomeBack = "மீண்டும் வருக"
    override val createAccount = "கணக்கை உருவாக்கு"
    override val dontHaveAccount = "கணக்கு இல்லையா? "
    override val alreadyHaveAccount = "ஏற்கனவே கணக்கு உள்ளதா? "
    override val joinOrCare = "சிறந்த வாய் ஆரோக்கியத்திற்காக ORCare இல் சேரவும்"
    override val signInToContinue = "உங்கள் வாய் ஆரோக்கிய பயணத்தைத் தொடர உள்நுழையவும்"
    override val enterEmailToReset = "கடவுச்சொல்லை மீட்டமைக்க உங்கள் மின்னஞ்சலை உள்ளிடவும்"
    override val sendOtp = "OTP ஐ அனுப்பு"
    override val backToLogin = "மீண்டும் உள்நுழைவு"
    override val verification = "சரிபார்ப்பு"
    override val verified = "சரிபார்க்கப்பட்டது"
    override val otpVerification = "OTP சரிபார்ப்பு"
    
    override val hello = "வணக்கம்"
    override val homeQuestion = "இன்று நீங்கள் எந்த வாய் சுகாதார நோயைப் பற்றி ஆய்வு செய்ய வந்துள்ளீர்கள்?"
    override val aiAssistant = "AI சுகாதார உதவியாளர்"
    override val aiAssistantSubtitle = "உங்கள் தனிப்பட்ட வாய் பராமரிப்பு நிபுணர்"
    override val quickActions = "விரைவான செயல்கள்"
    override val complaintChecker = "புகார் சரிபார்ப்பு"
    override val learningCenter = "கற்றல் மையம்"
    override val dailyTips = "தினசரி குறிப்புகள்"
    override val commonSymptoms = "பொதுவான அறிகுறிகள்"
    override val dailyBrushingTip = "தினசரி துலக்குதல் குறிப்பு"
    override val oralDiseases = "வாய் நோய்கள்"
    override val commonOralConditions = "பொதுவான வாய் நிலைமைகள்"
    override val oralDiseaseSubtitle = "பொதுவான வாய் சுகாதாரப் பிரச்சனைகள், எதைப் பார்க்க வேண்டும் மற்றும் எப்போது பல் மருத்துவரைப் பார்க்க வேண்டும் என்பதைப் பற்றி அறியவும்."
    
    override val profile = "சுயவிவரம்"
    override val logout = "வெளியேறு"
    override val members = "OrCare உறுப்பினர்"
    override val settings = "அமைப்புகள்"
    override val reminders = "நினைவூட்டல்கள்"
    override val privacySecurity = "தனியுரிமை மற்றும் தரவு பாதுகாப்பு"
    override val privacyPolicy = "தனியுரிமைக் கொள்கை"
    override val helpFeedback = "உதவி / கருத்து"
    override val language = "மொழி"
    override val saveChanges = "மாற்றங்களைச் சேமி"
    override val deleteAccount = "கணக்கை நீக்கு"
    override val editProfile = "சுயவிவரத்தைத் திருத்து"
    override val emailAddress = "மின்னஞ்சல் முகவரி"
    override val location = "இடம்"
    
    override val navHome = "முகப்பு"
    override val navChatbot = "உதவியாளர்"
    override val navEducation = "கற்றல்"
    override val navProfile = "சுயவிவரம்"

    override val fullName = "முழு பெயர்"
    override val ageLabel = "வயது"
    override val genderLabel = "பாலினம்"
    override val confirmPassword = "கடவுச்சொல்லை உறுதிப்படுத்தவும்"
    override val selectGender = "பாலினத்தைத் தேர்ந்தெடுக்கவும்"
    
    override val skip = "தவிர்"
    override val continueLabel = "தொடரவும்"
    override val getStarted = "தொடங்குங்கள்"
    override val onboarding1Title = "AI-இயங்கும் பல் மருத்துவம்"
    override val onboarding1Desc = "உங்கள் வாய் ஆரோக்கிய கேள்விகளுக்கு உடனடி பதில்களைப் பெறுங்கள். எங்கள் AI அரட்டை உங்கள் மொழியில் 24/7 வழிகாட்டலை வழங்குகிறது."
    override val onboarding2Title = "ஸ்மார்ட் அறிகுறி சரிபார்ப்பு"
    override val onboarding2Desc = "உங்கள் பல் அறிகுறிகளைச் சரிபார்த்து, எப்போது பல் மருத்துவரைப் பார்க்க வேண்டும் என்பது குறித்த தனிப்பயனாக்கப்பட்ட ஆலோசனையைப் பெறுங்கள்."
    override val onboarding3Title = "கற்றல் தொகுதிகள் மற்றும் வினாடி வினாக்கள்"
    override val onboarding3Desc = "கிராமப்புற சமூகங்களுக்காக வடிவமைக்கப்பட்ட வீடியோக்கள், கட்டுரைகள் மற்றும் ஊடாடும் வினாடி வினாக்களுடன் கல்வி உள்ளடக்கத்தை அணுகவும்."
    override val onboarding4Title = "பல மொழி ஆதரவு"
    override val onboarding4Desc = "ஆங்கிலம், தமிழ், தெலுங்கு அல்லது இந்தியில் பயன்பாட்டைப் பயன்படுத்தவும். அமைப்புகளில் இருந்து எப்போது வேண்டுமானாலும் மொழிகளை மாற்றலாம்."
    override val onboarding5Title = "தினசரி உதவிக்குறிப்புகள் மற்றும் நினைவூட்டல்கள்"
    override val onboarding5Desc = "தனிப்பயனாக்கப்பட்ட வாய் ஆரோக்கியக் குறிப்புகளைப் பெறுங்கள் மற்றும் துலக்குதல் மற்றும் பல் பரிசோதனைகளுக்கு நினைவூட்டல்களை அமைக்கவும்."
    
    override val chooseLanguage = "மொழியைத் தேர்ந்தெடுக்கவும்"
    override val chatHistory = "உரையாடல் வரலாறு"
    override val startNewChat = "புதிய அரட்டையைத் தொடங்கவும்"
    override val askAnything = "ஏதாவது கேளுங்கள்..."
    override val orcareAI = "ORCare AI"
    
    override val checkDentalComplaints = "உங்கள் பல் புகார்களைச் சரிபார்க்கவும்"
    override val selectSymptomDesc = "சாத்தியமான காரணங்கள் மற்றும் வீட்டு பராமரிப்பு குறிப்புகள் பற்றி மேலும் அறிய ஒரு அறிகுறியைத் தேர்ந்தெடுக்கவும்"
    
    override val learningPath = "கற்றல் பாதை"
    override val featured = "சிறப்பு"
    override val startLesson = "பாடத்தைத் தொடங்குங்கள்"
    override val learningHeader = "பிரகாசமான புன்னகைக்கான உங்கள் பாதை"
    override val learningSubtitle = "தொழில்முறை வழிகாட்டுதலுடன் உங்கள் பல் ஆரோக்கியத்தில் தேர்ச்சி பெறுங்கள்."
    override val professionalModules = "தொழில்முறை தொகுதிகள்"
}

class TeluguStrings : ORCareStrings {
    override val appName = "ORCare"
    override val email = "ఈమెయిల్ చిరునామా"
    override val password = "పాస్‌వర్డ్"
    override val login = "సైన్ ఇన్"
    override val signUp = "సైన్ అప్"
    override val forgotPassword = "పాస్‌వర్డ్ మర్చిపోయారా?"
    override val welcomeBack = "తిరిగి స్వాగతం"
    override val createAccount = "ఖాతాను సృష్టించండి"
    override val dontHaveAccount = "ఖాతా లేదా? "
    override val alreadyHaveAccount = "ముందే ఖాతా ఉందా? "
    override val joinOrCare = "మెరుగైన నోటి ఆరోగ్యం కోసం ORCareలో చేరండి"
    override val signInToContinue = "మీ నోటి ఆరోగ్య ప్రయాణాన్ని కొనసాగించడానికి సైన్ ఇన్ చేయండి"
    override val enterEmailToReset = "పాస్‌వర్డ్ రీసెట్ చేయడానికి మీ ఇమెయిల్‌ను నమోదు చేయండి"
    override val sendOtp = "OTP పంపండి"
    override val backToLogin = "మళ్ళీ లాగిన్ చేయండి"
    override val verification = "ధృవీకరణ"
    override val verified = "ధృవీకరించబడింది"
    override val otpVerification = "OTP ధృవీకరణ"
    
    override val hello = "నమస్కారం"
    override val homeQuestion = "ఈ రోజు మీరు ఏ నోటి ఆరోగ్య వ్యాధి గురించి అధ్యయనం చేయడానికి ఇక్కడికి వచ్చారు?"
    override val aiAssistant = "AI హెల్త్ అసిస్టెంట్"
    override val aiAssistantSubtitle = "మీ వ్యక్తిగత నోటి సంరక్షణ నిపుణుడు"
    override val quickActions = "త్వరిత చర్యలు"
    override val complaintChecker = "ఫిర్యాదు తనిఖీ"
    override val learningCenter = "లెర్నింగ్ సెంటర్"
    override val dailyTips = "రోజువారీ చిట్కాలు"
    override val commonSymptoms = "సాధారణ లక్షణాలు"
    override val dailyBrushingTip = "రోజువారీ బ్రషింగ్ చిట్కా"
    override val oralDiseases = "నోటి వ్యాధులు"
    override val commonOralConditions = "సాధారణ నోటి పరిస్థితులు"
    override val oralDiseaseSubtitle = "సాధారణ నోటి ఆరోగ్య సమస్యలు, ఏ సంకేతాల కోసం వెతకాలి మరియు దంతవైద్యుడిని ఎప్పుడు సంప్రదించాలో తెలుసుకోండి."
    
    override val profile = "ప్రొఫైల్"
    override val logout = "లాగ్ అవుట్"
    override val members = "OrCare సభ్యుడు"
    override val settings = "సెట్టింగులు"
    override val reminders = "రిమైండర్లు"
    override val privacySecurity = "గోప్యత & డేటా భద్రత"
    override val privacyPolicy = "గోప్యతా విధానం"
    override val helpFeedback = "సహాయం / ఫీడ్‌బ్యాక్"
    override val language = "భాష"
    override val saveChanges = "మార్పులను సేవ్ చేయి"
    override val deleteAccount = "ఖాతాను తొలగించండి"
    override val editProfile = "ప్రొఫైల్ సవరించండి"
    override val emailAddress = "ఈమెయిల్ చిరునామా"
    override val location = "స్థానం"
    
    override val navHome = "హోమ్"
    override val navChatbot = "అసిస్టెంట్"
    override val navEducation = "నేర్చుకోండి"
    override val navProfile = "ప్రొఫైల్"

    override val fullName = "పూర్తి పేరు"
    override val ageLabel = "వయస్సు"
    override val genderLabel = "లింగం"
    override val confirmPassword = "పాస్‌వర్డ్‌ను నిర్ధారించండి"
    override val selectGender = "లింగాన్ని ఎంచుకోండి"
    
    override val skip = "దాటవేయి"
    override val continueLabel = "కొనసాగించండి"
    override val getStarted = "ప్రారంభించండి"
    override val onboarding1Title = "AI-ఆధారిత డెంటల్ చాట్‌బాట్"
    override val onboarding1Desc = "మీ నోటి ఆరోగ్య ప్రశ్నలకు తక్షణ సమాధానాలు పొందండి. మా AI చాట్‌బాట్ మీ భాషలో 24/7 మార్గదర్శకత్వాన్ని అందిస్తుంది."
    override val onboarding2Title = "స్మార్ట్ లక్షణ తనిఖీ"
    override val onboarding2Desc = "మీ దంత లక్షణాలను తనిఖీ చేయండి మరియు ఎప్పుడు దంతవైద్యుడిని సంప్రదించాలో వ్యక్తిగతీకరించిన సలహాలను పొందండి."
    override val onboarding3Title = "లెర్నింగ్ మాడ్యూల్స్ & క్విజ్‌లు"
    override val onboarding3Desc = "గ్రామీణ వర్గాల కోసం రూపొందించిన వీడియోలు, వ్యాసాలు మరియు ఇంటరాక్టివ్ క్విజ్‌లతో విద్యా కంటెంట్‌ను యాక్సెస్ చేయండి."
    override val onboarding4Title = "బహుభాషా మద్దతు"
    override val onboarding4Desc = "యాప్‌ను ఇంగ్లీష్, తమిళం, తెలుగు లేదా హిందీలో ఉపయోగించండి. సెట్టింగ్‌ల నుండి ఎప్పుడైనా భాషలను మార్చుకోవచ్చు."
    override val onboarding5Title = "రోజువారీ చిట్కాలు & రిమైండర్లు"
    override val onboarding5Desc = "వ్యక్తిగతీకరించిన నోటి ఆరోగ్య చిట్కాలను పొందండి మరియు బ్రషింగ్ మరియు దంత పరీక్షల కోసం రిమైండర్‌లను సెట్ చేయండి।"
    
    override val chooseLanguage = "భాషను ఎంచుకోండి"
    override val chatHistory = "చాట్ చరిత్ర"
    override val startNewChat = "కొత్త చాట్‌ను ప్రారంభించండి"
    override val askAnything = "ఏదైనా అడగండి..."
    override val orcareAI = "ORCare AI"
    
    override val checkDentalComplaints = "మీ దంత ఫిర్యాదులను తనిఖీ చేయండి"
    override val selectSymptomDesc = "సాధ్యమయ్యే కారణాలు మరియు ఇంటి సంరక్షణ చిట్కాల గురించి మరింత తెలుసుకోవడానికి లక్షణాన్ని ఎంచుకోండి"
    
    override val learningPath = "లెర్నింగ్ పాత్"
    override val featured = "ఫీచర్ చేయబడింది"
    override val startLesson = "పాఠాన్ని ప్రారంభించండి"
    override val learningHeader = "ప్రకాశవంతమైన చిరునవ్వుకు మీ మార్గం"
    override val learningSubtitle = "వృత్తిపరమైన మార్గదర్శకత్వంతో మీ దంత ఆరోగ్యాన్ని పెంపొందించుకోండి."
    override val professionalModules = "వృత్తిపరమైన మాడ్యూల్స్"
}

class HindiStrings : ORCareStrings {
    override val appName = "ORCare"
    override val email = "ईमेल पता"
    override val password = "पासवर्ड"
    override val login = "साइन इन"
    override val signUp = "साइन अप"
    override val forgotPassword = "पासवर्ड भूल गए?"
    override val welcomeBack = "वापस स्वागत है"
    override val createAccount = "खाता बनाएं"
    override val dontHaveAccount = "खाता नहीं है? "
    override val alreadyHaveAccount = "पहले से ही एक खाता है? "
    override val joinOrCare = "बेहतर मौखिक स्वास्थ्य के लिए ORCare से जुड़ें"
    override val signInToContinue = "अपनी मौखिक स्वास्थ्य यात्रा जारी रखने के लिए साइन इन करें"
    override val enterEmailToReset = "पासवर्ड रीसेट करने के लिए अपना ईमेल दर्ज करें"
    override val sendOtp = "ओटीपी भेजें"
    override val backToLogin = "लॉगिन पर वापस जाएं"
    override val verification = "सत्यापन"
    override val verified = "सत्यापित"
    override val otpVerification = "ओटीपी सत्यापन"
    
    override val hello = "नमस्ते"
    override val homeQuestion = "आज आप किस मौखिक स्वास्थ्य बीमारी का अध्ययन करने के लिए यहाँ आए हैं?"
    override val aiAssistant = "AI स्वास्थ्य सहायक"
    override val aiAssistantSubtitle = "आपका व्यक्तिगत मौखिक देखभाल विशेषज्ञ"
    override val quickActions = "त्वरित क्रियाएं"
    override val complaintChecker = "शिकायत जांचकर्ता"
    override val learningCenter = "लर्निंग सेंटर"
    override val dailyTips = "दैनिक सुझाव"
    override val commonSymptoms = "सामान्य लक्षण"
    override val dailyBrushingTip = "दैनिक ब्रशिंग टिप"
    override val oralDiseases = "मौखिक रोग"
    override val commonOralConditions = "सामान्य मौखिक स्थितियां"
    override val oralDiseaseSubtitle = "सामान्य मौखिक स्वास्थ्य समस्याओं के बारे में जानें, क्या लक्षण देखें और कब दंत चिकित्सक को दिखाएं।"
    
    override val profile = "प्रोफ़ाइल"
    override val logout = "लॉग आउट"
    override val members = "OrCare सदस्य"
    override val settings = "सेटिंग्स"
    override val reminders = "अनुस्मारक"
    override val privacySecurity = "गोपनीयता और डेटा सुरक्षा"
    override val privacyPolicy = "गोपनीयता नीति"
    override val helpFeedback = "सहायता / प्रतिक्रिया"
    override val language = "भाषा"
    override val saveChanges = "परिवर्तन सहेजें"
    override val deleteAccount = "खाता हटाएं"
    override val editProfile = "प्रोफ़ाइल संपादित करें"
    override val emailAddress = "ईमेल पता"
    override val location = "स्थान"
    
    override val navHome = "होम"
    override val navChatbot = "सहायक"
    override val navEducation = "सीखें"
    override val navProfile = "प्रोफ़ाइल"

    override val fullName = "पूरा नाम"
    override val ageLabel = "आयु"
    override val genderLabel = "लिंग"
    override val confirmPassword = "पासवर्ड की पुष्टि करें"
    override val selectGender = "लिंग चुनें"
    
    override val skip = "छोड़ें"
    override val continueLabel = "जारी रखें"
    override val getStarted = "शुरू करें"
    override val onboarding1Title = "AI-संचालित डेंटल चैटबॉट"
    override val onboarding1Desc = "अपने मौखिक स्वास्थ्य संबंधी प्रश्नों के उत्तर तुरंत प्राप्त करें। हमारा AI चैटबॉट आपकी भाषा में 24/7 मार्गदर्शन प्रदान करता है।"
    override val onboarding2Title = "स्मार्ट लक्षण जांचकर्ता"
    override val onboarding2Desc = "अपने दंत लक्षणों की जांच करें और दंत चिकित्सक को कब दिखाना है, इस पर व्यक्तिगत सलाह प्राप्त करें।"
    override val onboarding3Title = "लर्निंग मॉड्यूल और क्विज़"
    override val onboarding3Desc = "ग्रामीण समुदायों के लिए डिज़ाइन किए गए वीडियो, लेख और इंटरैक्टिव क्विज़ के साथ शैक्षिक सामग्री तक पहुँचें।"
    override val onboarding4Title = "बहु-भाषा समर्थन"
    override val onboarding4Desc = "अंग्रेजी, तमिल, तेलुगु या हिंदी में ऐप का उपयोग करें। सेटिंग्स से कभी भी भाषा बदलें।"
    override val onboarding5Title = "दैनिक सुझाव और अनुस्मारक"
    override val onboarding5Desc = "व्यक्तिगत मौखिक स्वास्थ्य सुझाव प्राप्त करें और ब्रश करने और दंत जांच के लिए अनुस्मारक सेट करें।"
    
    override val chooseLanguage = "भाषा चुनें"
    override val chatHistory = "चैट इतिहास"
    override val startNewChat = "नई चैट शुरू करें"
    override val askAnything = "कुछ भी पूछें..."
    override val orcareAI = "ORCare AI"
    
    override val checkDentalComplaints = "अपनी दंत शिकायतों की जाँच करें"
    override val selectSymptomDesc = "संभावित कारणों और घरेलू देखभाल युक्तियों के बारे में अधिक जानने के लिए एक लक्षण चुनें"
    
    override val learningPath = "सीखने का मार्ग"
    override val featured = "विशेष"
    override val startLesson = "पाठ शुरू करें"
    override val learningHeader = "एक उज्ज्वल मुस्कान के लिए आपका मार्ग"
    override val learningSubtitle = "पेशेवर मार्गदर्शन के साथ अपने दంత स्वास्थ्य में महारत हासिल करें।"
    override val professionalModules = "पेशेवर मॉड्यूल"
}

val LocalORCareStrings = staticCompositionLocalOf<ORCareStrings> {
    EnglishStrings()
}
