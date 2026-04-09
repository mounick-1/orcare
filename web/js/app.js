/**
 * app.js — ORCare Web Application Controller
 * Handles routing, screen management, data loading, state.
 */

const App = (() => {

  // ── Screens with bottom-nav visibility ──────────────────────
  const MAIN_SCREENS = ['home','chatbot','diseases','symptoms','learning','tips','profile','disease-detail','symptom-detail','edit-profile','reminders','privacy','help','learning-cat','module-det','lesson','quiz'];
  const NAV_SCREENS  = ['home','chatbot','diseases','learning','profile']; // show bottom nav

  let currentMainScreen = 'home';
  let currentAuthScreen = 'login';
  const AUTH_SCREENS = ['login','signup','forgot','otp','reset-sent'];

  // ── Entry Point ─────────────────────────────────────────────
  function init() {
    console.log('ORCare Initialization...');
    
    // Init sub-modules
    Auth.init();
    Chat.init();

    // Simulate Splash Screen delay then route
    setTimeout(() => {
      const language = localStorage.getItem('orcare_lang');
      const onboardingCompleted = localStorage.getItem('orcare_onboarding');
      const token = localStorage.getItem('orcare_token');

      // Hide splash
      const splash = document.getElementById('screen-splash');
      if (splash) {
        splash.style.opacity = '0';
        setTimeout(() => {
          splash.classList.remove('active');
          
          if (!language) {
            showSetup('language');
          } else if (!onboardingCompleted) {
            showSetup('onboarding');
          } else if (!token) {
            showAuthWrapper();
          } else {
            onLoginSuccess();
          }
        }, 600);
      }
    }, 2500);
  }

  // ── Setup Flow (Language & Onboarding) ──────────────────
  function showSetup(screen) {
    document.getElementById('setup-wrapper').classList.remove('hidden');
    document.getElementById('auth-wrapper').classList.add('hidden');
    document.getElementById('main-wrapper').classList.add('hidden');
    
    ['language', 'onboarding'].forEach(s => {
      const el = document.getElementById(`screen-${s}`);
      if (el) el.classList.toggle('active', s === screen);
    });
  }

  function selectLanguage(lang) {
    localStorage.setItem('orcare_lang', lang);
    document.querySelectorAll('.lang-card').forEach(card => {
      card.classList.toggle('active', card.dataset.lang === lang);
    });
    document.getElementById('lang-continue-btn').classList.remove('hidden');
  }

  function proceedFromLanguage() {
    showSetup('onboarding');
  }

  let onboardingIndex = 0;
  const onboardingCount = 5;

  function nextOnboarding() {
    if (onboardingIndex < onboardingCount - 1) {
      onboardingIndex++;
      updateOnboarding();
    } else {
      completeOnboarding();
    }
  }

  function updateOnboarding() {
    const slider = document.getElementById('onboarding-slider');
    slider.style.transform = `translateX(-${onboardingIndex * 100}%)`;
    
    document.querySelectorAll('.onboarding-dots .dot').forEach((dot, idx) => {
      dot.classList.toggle('active', idx === onboardingIndex);
    });
    
    const btn = document.getElementById('onboarding-btn');
    btn.textContent = (onboardingIndex === onboardingCount - 1) ? 'Get Started' : 'Continue';
  }

  function completeOnboarding() {
    localStorage.setItem('orcare_onboarding', 'true');
    Auth.show();
  }

  // ── Auth navigation ─────────────────────────────────────────
  function navigateTo(screen) {
    if (!AUTH_SCREENS.includes(screen)) return;
    AUTH_SCREENS.forEach(s => {
      const el = document.getElementById(`screen-${s}`);
      if (el) el.classList.toggle('active', s === screen);
    });
    currentAuthScreen = screen;
  }

  function showAuthWrapper() {
    document.getElementById('auth-wrapper').classList.remove('hidden');
    document.getElementById('main-wrapper').classList.add('hidden');
    navigateTo('login');
  }

  function showMainWrapper() {
    document.getElementById('auth-wrapper').classList.add('hidden');
    document.getElementById('main-wrapper').classList.remove('hidden');
  }

  function onLoginSuccess() {
    showMainWrapper();
    Auth.populateProfile();
    loadHomeData();
    showMain('home');
  }

  // ── Main screen routing ──────────────────────────────────────
  function showMain(screen) {
    if (!MAIN_SCREENS.includes(screen)) return;

    MAIN_SCREENS.forEach(s => {
      const el = document.getElementById(`screen-${s}`);
      if (el) el.classList.toggle('active', s === screen);
    });

    // Update bottom nav active state
    const showNav = NAV_SCREENS.includes(screen) || screen === 'chatbot';
    document.getElementById('bottom-nav').style.display = showNav ? '' : '';
    document.querySelectorAll('.nav-item').forEach(btn => {
      btn.classList.toggle('active', btn.dataset.screen === screen);
    });

    // Update desktop sidebar active state
    document.querySelectorAll('.sidebar-item').forEach(item => {
      item.classList.toggle('active', item.dataset.screen === screen);
    });

    currentMainScreen = screen;

    // Lazy-load data per screen
    if (screen === 'diseases') loadDiseases();
    if (screen === 'learning') loadLearning();
    if (screen === 'tips')     loadTips();
    if (screen === 'profile')  Auth.populateProfile();
  }

  function showDiseaseDetail(d) {
    document.getElementById('det-name').textContent = d.name;
    document.getElementById('det-title').textContent = d.name;
    document.getElementById('det-icon-wrap').textContent = d.icon || '🦷';
    document.getElementById('det-icon-wrap').style.background = d.color || 'var(--primary)';
    
    document.getElementById('det-happening').textContent = d.whatIsHappening || d.desc || '---';
    document.getElementById('det-notice').textContent = d.whatPeopleNotice || 'Visible changes or discomfort in the affected area.';
    document.getElementById('det-why').textContent = d.whyItHappens || 'Bacterial accumulation or poor oral hygiene habits.';
    document.getElementById('det-ignore').textContent = d.whyNotIgnore || 'Can lead to severe pain, infection, or tooth loss.';
    document.getElementById('det-dentist').textContent = d.whenToSeeDentist || 'Visit a dentist if you experience persistent pain or visible changes.';
    
    showMain('disease-detail');
  }

  const SYMPTOMS_INFO = {
    'Tooth Pain': {
      icon: '🦷',
      happening: 'Tooth pain often signals a problem with the tooth or the surrounding gums.',
      notice: 'Sharp or dull ache, sensitivity to temperature, and pain when biting down.',
      reasons: 'Cavities, gum disease, abscessed tooth, or tooth fracture.',
      todo: 'Rinse with warm salt water, use floss to remove trapped food, and avoid extremely hot or cold food.',
      dentist: 'See a dentist immediately if pain is severe, persistent, or accompanied by fever or swelling.'
    },
    'Bleeding Gums': {
      icon: '🩸',
      happening: 'Gums that bleed easily are often inflamed or irritated by plaque buildup.',
      notice: 'Red, swollen, or tender gums that bleed during brushing or flossing.',
      reasons: 'Gingivitis, brushing too hard, or improper flossing technique.',
      todo: 'Brush gently with a soft toothbrush, floss daily, and use an antimicrobial mouthwash.',
      dentist: 'Consult a dentist if bleeding persists for more than a week or if gums are receding.'
    },
    'Bad Breath': {
      icon: '😮‍💨',
      happening: 'Chronic bad breath (halitosis) is typically caused by bacteria in the mouth.',
      notice: 'Persistent unpleasant odor, dry mouth, or a white coating on the tongue.',
      reasons: 'Poor oral hygiene, dry mouth, strongly scented foods, or tobacco use.',
      todo: 'Brush and floss twice daily, use a tongue scraper, and stay hydrated.',
      dentist: 'See a dentist if bad breath doesn\'t improve with better oral hygiene.'
    },
    'Sensitivity': {
      icon: '⚡',
      happening: 'Sensitive teeth occur when the underlying layer of your teeth (dentin) becomes exposed.',
      notice: 'Sudden, sharp pain when consuming hot, cold, sweet, or acidic foods and drinks.',
      reasons: 'Worn tooth enamel, exposed tooth roots, or gum recession.',
      todo: 'Use desensitizing toothpaste, avoid acidic foods, and use a soft-bristled brush.',
      dentist: 'Consult a dentist to rule out cavities or loose fillings causing the sensitivity.'
    },
    'Swelling': {
      icon: '🤕',
      happening: 'Swelling in the gums or face usually indicates an infection or inflammation.',
      notice: 'Puffy, red, or tender areas that may be warm to the touch.',
      reasons: 'Abscess, gum disease, or wisdom tooth issues.',
      todo: 'Apply a cold compress to the outside of the cheek and rinse with salt water.',
      dentist: 'Urgent: See a dentist immediately as this could indicate a serious infection.'
    },
    'Jaw Pain': {
      icon: '😬',
      happening: 'Pain in the jaw can stem from the joint itself or the muscles that control it.',
      notice: 'Difficulty opening the mouth, clicking sounds, or ache near the ear.',
      reasons: 'TMJ disorders, teeth grinding (bruxism), or stress-related muscle tension.',
      todo: 'Try jaw relaxation exercises, avoid hard foods, and manage stress.',
      dentist: 'Consult a dentist to check for TMJ issues or signs of grinding.'
    },
    'Tooth Decay': {
      icon: '🦠',
      happening: 'Decay happens when acids produced by bacteria eat away at the tooth enamel.',
      notice: 'Visible holes or pits, brown or black stains, and localized pain.',
      reasons: 'High sugar diet, poor brushing, and lack of fluoride.',
      todo: 'Limit sugary snacks, use fluoride toothpaste, and drink plenty of water.',
      dentist: 'See a dentist to get a filling before the decay reaches the nerve.'
    },
    'Gum Recession': {
      icon: '📉',
      happening: 'Recession is when the gum tissue pulls away, exposing the root of the tooth.',
      notice: 'Teeth appearing longer, increased sensitivity, and a notch near the gum line.',
      reasons: 'Aggressive brushing, gum disease, or genetics.',
      todo: 'Switch to a soft toothbrush, brush gently, and maintain good hygiene.',
      dentist: 'See a dentist to prevent further recession and bone loss.'
    },
    'Mouth Ulcer': {
      icon: '🔴',
      happening: 'Ulcers are small, painful sores that develop inside the mouth.',
      notice: 'Round or oval sores with a white or yellow center and a red border.',
      reasons: 'Minor injury, stress, hormonal changes, or nutritional deficiencies.',
      todo: 'Avoid spicy or acidic foods, use a mouth sore gel, and rinse with salt water.',
      dentist: 'Consult a dentist if an ulcer lasts longer than two weeks or is unusually large.'
    }
  };

  function showSymptomDetail(name) {
    const info = SYMPTOMS_INFO[name] || {
      icon: '🔍',
      happening: 'Please consult our AI assistant for more details on this symptom.',
      notice: 'Varies by individual.',
      reasons: 'Multiple possible factors.',
      todo: 'Maintain good oral hygiene.',
      dentist: 'Consult a professional if symptoms persist.'
    };

    document.getElementById('sym-name').textContent = name;
    document.getElementById('sym-title').textContent = name;
    document.getElementById('sym-icon-wrap').textContent = info.icon;
    
    document.getElementById('sym-happening').textContent = info.happening;
    document.getElementById('sym-notice').textContent = info.notice;
    document.getElementById('sym-reasons').textContent = info.reasons;
    document.getElementById('sym-todo').textContent = info.todo;
    document.getElementById('sym-dentist').textContent = info.dentist;

    showMain('symptom-detail');
  }

  // ── Data Loaders ─────────────────────────────────────────────

  const DAILY_TIPS = [
    'Brush your teeth at least twice a day with a soft-bristled toothbrush.',
    'Floss daily to remove plaque and food particles between teeth.',
    'Rinse with fluoride mouthwash to strengthen enamel and kill bacteria.',
    'Replace your toothbrush every 3–4 months or sooner if bristles fray.',
    'Drink plenty of water throughout the day to keep your mouth hydrated.',
    'Limit sugary and acidic foods — they erode enamel over time.',
    'Visit your dentist every 6 months for a professional cleaning and checkup.',
    'Use a tongue scraper to remove bacteria and improve breath.',
    'Chew sugar-free gum after meals to stimulate saliva production.',
    'Never use your teeth as tools to open packages or bottles.',
  ];

  const DISEASES_LOCAL = [
    { 
      icon:'🦷', name:'Dental Caries (Cavities)', desc:'Bacterial infection causing tooth decay', color:'#FF8C42',
      whatIsHappening: 'Dental caries, also known as cavities or tooth decay, is the breakdown of teeth due to acids made by bacteria.',
      whatPeopleNotice: 'Toothache, tooth sensitivity, mild to sharp pain when eating or drinking something sweet, hot or cold.',
      whyItHappens: 'Frequent snacking, sipping sugary drinks, and not cleaning your teeth well create an environment for decay.',
      whyNotIgnore: 'If left untreated, cavities can lead to severe toothache, infection, and tooth loss.',
      whenToSeeDentist: 'Visit immediately if you notice holes or pits in your teeth or experience spontaneous pain.'
    },
    { icon:'🩸', name:'Gingivitis',              desc:'Early-stage gum inflammation',             color:'#EF4444' },
    { icon:'💀', name:'Periodontitis',           desc:'Advanced gum disease affecting bone',       color:'#8B5CF6' },
    { icon:'🔴', name:'Oral Ulcers',             desc:'Painful sores inside the mouth',            color:'#F59E0B' },
    { icon:'😮‍💨', name:'Halitosis (Bad Breath)', desc:'Persistent unpleasant mouth odour',        color:'#6EE7B7' },
    { icon:'⚡', name:'Tooth Sensitivity',       desc:'Pain from hot, cold, or sweet stimuli',     color:'#3B82F6' },
    { icon:'🌡', name:'Dental Abscess',          desc:'Pocket of pus caused by bacterial infection', color:'#DC2626' },
    { icon:'🫦', name:'Gum Recession',           desc:'Gum tissue pulls back from teeth',          color:'#D97706' },
    { icon:'😬', name:'Bruxism (Teeth Grinding)','desc':'Unconscious grinding or clenching',       color:'#7C3AED' },
    { icon:'🦠', name:'Oral Thrush',             desc:'Fungal infection causing white patches',     color:'#10B981' },
  ];

  const LEARNING_LOCAL = [
    {
      icon: '🪥', title: 'Oral Hygiene Basics',
      modules: [
        { icon:'✦', title:'Brushing Techniques' },
        { icon:'✦', title:'Flossing Daily' },
        { icon:'✦', title:'Mouthwash Guide' },
      ]
    },
    {
      icon: '🦷', title: 'Understanding Tooth Decay',
      modules: [
        { icon:'✦', title:'What Causes Cavities?' },
        { icon:'✦', title:'Sugar & Your Teeth' },
        { icon:'✦', title:'Fluoride & Protection' },
      ]
    },
    {
      icon: '🩸', title: 'Gum Disease Prevention',
      modules: [
        { icon:'✦', title:'Gingivitis Explained' },
        { icon:'✦', title:'Periodontitis Risks' },
        { icon:'✦', title:'Gum Care Routine' },
      ]
    },
    {
      icon: '😄', title: 'Cosmetic Dental Care',
      modules: [
        { icon:'✦', title:'Teeth Whitening' },
        { icon:'✦', title:'Veneers vs Bonding' },
        { icon:'✦', title:'Smile Makeovers' },
      ]
    },
  ];

  function loadHomeData() {
    // Tip of the day (random)
    const tip = DAILY_TIPS[new Date().getDay() % DAILY_TIPS.length];
    document.getElementById('tip-text').textContent = tip;
  }

  async function loadDiseases() {
    const container = document.getElementById('diseases-list');
    if (container.dataset.loaded === '1') return;

    container.innerHTML = `<div class="loading-state"><div class="loader"></div>Loading…</div>`;

    // Try backend first, fall back to local data
    let diseases = DISEASES_LOCAL;
    const { ok, data } = await API.getDiseases();
    if (ok && Array.isArray(data) && data.length > 0) {
      diseases = data.map(d => ({
        icon: d.iconEmoji || '🦷',
        name: d.name,
        desc: d.whatIsHappening?.slice(0, 80) || 'Tap to learn more',
      }));
    }

    container.innerHTML = '';
    diseases.forEach(d => {
      const card = document.createElement('div');
      card.className = 'disease-card';
      card.innerHTML = `
        <div class="disease-icon-wrap">${d.icon}</div>
        <div class="disease-info">
          <div class="disease-name">${esc(d.name)}</div>
          <div class="disease-sub">${esc(d.desc || 'Tap to learn more')}</div>
          <span class="disease-tag">Oral Disease</span>
        </div>
        <span class="disease-arrow">›</span>`;
      card.addEventListener('click', () => showDiseaseDetail(d));
      container.appendChild(card);
    });

    container.dataset.loaded = '1';
  }

  async function loadLearning() {
    const grid = document.getElementById('learning-grid');
    if (grid.dataset.loaded === '1') return;

    grid.innerHTML = `<div class="loading-state"><div class="loader"></div>Loading…</div>`;

    let categories = LEARNING_LOCAL;
    const { ok, data } = await API.getLearning();
    if (ok && Array.isArray(data) && data.length > 0) {
      categories = data.map(cat => ({
        icon: cat.iconEmoji || '📚',
        title: cat.title,
        modules: (cat.modules || []).slice(0, 3).map(m => ({ icon: '✦', title: m.title }))
      }));
    }

    grid.innerHTML = '';
    categories.forEach(cat => {
      const card = document.createElement('div');
      card.className = 'learn-card';
      const modulesHtml = (cat.modules || []).map(m =>
        `<div class="module-item"><span>${m.icon}</span><span>${esc(m.title)}</span></div>`
      ).join('');
      card.innerHTML = `
        <div class="learn-card-header">
          <div class="learn-icon">${cat.icon}</div>
          <div>
            <div class="learn-title">${esc(cat.title)}</div>
            <div class="learn-sub">${(cat.modules||[]).length} modules</div>
          </div>
        </div>
        <div class="learn-modules">${modulesHtml}</div>`;
      card.addEventListener('click', () => showLearningCategory(cat));
      grid.appendChild(card);
    });

    grid.dataset.loaded = '1';
  }

  function showLearningCategory(cat) {
    document.getElementById('lcat-title').textContent = cat.title;
    const list = document.getElementById('lcat-list');
    list.innerHTML = '';
    
    (cat.modules || []).forEach((m, i) => {
       const item = document.createElement('div');
       item.className = 'learn-card';
       item.innerHTML = `
         <div class="learn-card-header" style="margin-bottom:0;">
           <div class="learn-icon" style="font-size:24px; width:44px; height:44px;">${m.icon || '✦'}</div>
           <div style="flex:1;">
             <div class="learn-title">${esc(m.title)}</div>
             <div class="learn-sub">3 Lessons • 15 mins</div>
           </div>
           <span class="disease-arrow">›</span>
         </div>
       `;
       item.addEventListener('click', () => showModuleDetail(m));
       list.appendChild(item);
    });
    
    showMain('learning-cat');
  }

  function showModuleDetail(module) {
    document.getElementById('mod-title').textContent = module.title;
    const list = document.getElementById('mod-lessons');
    list.innerHTML = '';
    
    const lessons = [
      { num: 1, title: 'Introduction to ' + module.title, dur: '3:00' },
      { num: 2, title: 'Best Practices & Techniques', dur: '5:30' },
      { num: 3, title: 'Common Mistakes to Avoid', dur: '4:15' }
    ];
    
    lessons.forEach(l => {
      const el = document.createElement('div');
      el.className = 'lesson-item';
      el.innerHTML = `
        <div class="lesson-num">${l.num}</div>
        <div class="lesson-info">
          <div class="lesson-title-text">${esc(l.title)}</div>
          <div class="lesson-meta">Video • ${l.dur}</div>
        </div>
        <span class="disease-arrow">▶</span>
      `;
      el.addEventListener('click', () => startLesson(module, l));
      list.appendChild(el);
    });
    
    const quizCard = document.createElement('div');
    quizCard.className = 'lesson-item';
    quizCard.style.marginTop = '12px';
    quizCard.style.background = '#EEF2FF';
    quizCard.style.borderColor = 'rgba(59,130,246,0.3)';
    quizCard.innerHTML = `
      <div class="lesson-num" style="background:var(--primary); color:white;">?</div>
      <div class="lesson-info">
        <div class="lesson-title-text" style="color:var(--primary);">Module Knowledge Check</div>
        <div class="lesson-meta">Interactive Quiz • 5 questions</div>
      </div>
    `;
    quizCard.addEventListener('click', () => App.showToast('Quiz implementation coming soon!'));
    list.appendChild(quizCard);

    showMain('module-det');
  }

  function startLesson(module, lesson) {
     document.getElementById('lesson-title').textContent = `Lesson ${lesson.num}`;
     document.getElementById('lesson-subtitle').textContent = lesson.title;
     
     // Mock content
     document.getElementById('lesson-text').textContent = 
       `This is a detailed educational text for "${lesson.title}" within the "${module.title}" module. ` +
       `In a full implementation, this section would render rich HTML content, diagrams, and instructions sourced from the backend API. ` +
       `Follow the steps carefully and make sure to watch the attached video for a visual demonstration of the correct techniques.`;
       
     document.getElementById('lesson-complete-btn').onclick = () => {
       showToast('Lesson marked as complete! 🎉');
       showMain('module-det');
     };
     
     showMain('lesson');
  }

  function loadTips() {
    const list = document.getElementById('tips-list');
    if (list.dataset.loaded === '1') return;

    list.innerHTML = '';
    DAILY_TIPS.forEach((tip, i) => {
      const item = document.createElement('div');
      item.className = 'tip-item';
      item.innerHTML = `
        <div class="tip-number">TIP #${i + 1}</div>
        <div class="tip-text">${esc(tip)}</div>`;
      list.appendChild(item);
    });
    list.dataset.loaded = '1';
  }

  // ── Toast notification ────────────────────────────────────────
  let toastTimer = null;
  function showToast(msg, duration = 2800) {
    const el = document.getElementById('toast');
    el.textContent = msg;
    el.classList.remove('hidden');
    el.classList.add('show');
    clearTimeout(toastTimer);
    toastTimer = setTimeout(() => {
      el.classList.remove('show');
      setTimeout(() => el.classList.add('hidden'), 350);
    }, duration);
  }

  // ── Utility ───────────────────────────────────────────────────
  function esc(t) {
    if (!t) return '';
    return String(t).replace(/&/g,'&amp;').replace(/</g,'&lt;').replace(/>/g,'&gt;');
  }

  // ── Public API ────────────────────────────────────────────────
  return { 
    init, 
    navigateTo, 
    showMain, 
    showAuth: showAuthWrapper, 
    onLoginSuccess, 
    showToast,
    selectLanguage,
    proceedFromLanguage,
    completeOnboarding,
    showDiseaseDetail,
    showSymptomDetail,
    cycleAvatar,
    showLearningCategory,
    showModuleDetail,
    startLesson
  };

})();

// ── Boot ──────────────────────────────────────────────────────
document.addEventListener('DOMContentLoaded', () => App.init());
