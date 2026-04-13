export interface Tip {
  id: number;
  title: string;
  description: string;
  category: string;
  icon: string;
}

export const tipCategories = ['All', 'Hygiene', 'Food', 'Lifestyle', 'Myth Busting', 'Age 7-9'];

export const tipsList: Tip[] = [
  { id: 1, title: 'Brush at Night', description: 'Brush at night before sleeping — it is the most important brushing of the day.', category: 'Hygiene', icon: '🌙' },
  { id: 2, title: '2 Minutes Rule', description: 'Brush for at least 2 minutes to ensure all surfaces are clean.', category: 'Hygiene', icon: '⏱️' },
  { id: 3, title: 'Soft Bristles', description: 'Always use a soft-bristled toothbrush to protect your gums and enamel.', category: 'Hygiene', icon: '🪥' },
  { id: 4, title: 'Tongue Cleaning', description: 'Clean your tongue daily to remove bacteria and freshen breath.', category: 'Hygiene', icon: '👅' },
  { id: 5, title: 'Replace Brush', description: 'Replace your toothbrush every 3 months or after recovering from illness.', category: 'Hygiene', icon: '🔄' },
  { id: 6, title: 'Fluoride Power', description: 'Use fluoride toothpaste to strengthen enamel and prevent cavities.', category: 'Hygiene', icon: '🦷' },
  { id: 7, title: "Spit, Don't Rinse", description: "Spit out excess toothpaste but don't rinse with water immediately.", category: 'Hygiene', icon: '🚫' },
  { id: 8, title: 'Floss Daily', description: "Flossing removes plaque from between teeth where your brush can't reach.", category: 'Hygiene', icon: '🧵' },
  { id: 9, title: 'Gentle Motion', description: 'Use gentle circular motions; scrubbing hard can damage gums.', category: 'Hygiene', icon: '💫' },
  { id: 10, title: 'Wait After Eating', description: 'Wait 30 minutes after eating acidic foods before brushing.', category: 'Hygiene', icon: '⏳' },
  { id: 11, title: 'Hydration', description: 'Drink plenty of water to wash away food particles and keep saliva flowing.', category: 'Food', icon: '💧' },
  { id: 12, title: 'Sugar Control', description: 'Limit sugary snacks and drinks to mealtimes to reduce acid attacks.', category: 'Food', icon: '🍬' },
  { id: 13, title: 'Tobacco Warning', description: 'Tobacco use significantly increases the risk of gum disease and oral cancer.', category: 'Lifestyle', icon: '🚭' },
  { id: 14, title: 'Mouthwash', description: 'Use an antimicrobial mouthwash to reduce plaque and gingivitis.', category: 'Hygiene', icon: '🧴' },
  { id: 15, title: 'Check Your Gums', description: "Healthy gums are pink and don't bleed. See a dentist if they do.", category: 'Hygiene', icon: '🔍' },
  { id: 16, title: 'Visit Dentist', description: 'Regular dental check-ups every 6 months are key to prevention.', category: 'Hygiene', icon: '👨‍⚕️' },
  { id: 17, title: 'Clean Between', description: 'Use interdental brushes for larger gaps between teeth.', category: 'Hygiene', icon: '🔧' },
  { id: 18, title: 'Limit Soda', description: 'Carbonated drinks, even sugar-free ones, can erode tooth enamel.', category: 'Food', icon: '🥤' },
  { id: 19, title: 'Healthy Snacks', description: 'Choose cheese, yogurt, or crunchy veggies instead of chips.', category: 'Food', icon: '🥕' },
  { id: 20, title: 'Protect Teeth', description: 'Wear a mouthguard during contact sports to prevent injuries.', category: 'Lifestyle', icon: '🛡️' },
  { id: 21, title: "Kids Brushing", description: "Supervise children's brushing until they are about 7 or 8 years old.", category: 'Age 7-9', icon: '👶' },
  { id: 22, title: 'Dry Mouth?', description: 'Chew sugar-free gum to stimulate saliva flow.', category: 'Lifestyle', icon: '🌵' },
  { id: 23, title: 'Stress & Teeth', description: 'Stress can lead to teeth grinding. Talk to your dentist about a nightguard.', category: 'Lifestyle', icon: '😬' },
  { id: 24, title: 'Vitamin C', description: 'Eat Vitamin C-rich foods like oranges for healthy gums.', category: 'Food', icon: '🍊' },
  { id: 25, title: 'Calcium', description: 'Dairy products provide calcium needed to keep teeth strong.', category: 'Food', icon: '🥛' },
  { id: 26, title: 'Straw Trick', description: 'Use a straw for sugary drinks to bypass teeth surfaces.', category: 'Lifestyle', icon: '🥤' },
  { id: 27, title: 'Whiten Safely', description: 'Ask your dentist before trying home whitening remedies.', category: 'Myth Busting', icon: '✨' },
  { id: 28, title: 'Bleeding Myth', description: 'Bleeding gums need MORE gentle brushing, not less.', category: 'Myth Busting', icon: '🩸' },
  { id: 29, title: 'Hard Brushing', description: 'Brushing harder does NOT clean better; it causes damage.', category: 'Myth Busting', icon: '🛑' },
  { id: 30, title: 'Baby Teeth', description: 'Baby teeth are important for spacing adult teeth. Keep them clean!', category: 'Age 7-9', icon: '🦷' },
  { id: 31, title: 'Whitening myths', description: 'Lemon juice does not whiten teeth safely. It can damage enamel.', category: 'Myth Busting', icon: '🍋' },
  { id: 32, title: 'Knocked-out tooth', description: 'If a tooth is knocked out, keep it moist in milk and see a dentist within 30 mins.', category: 'Hygiene', icon: '🥛' },
  { id: 33, title: 'Aspirin on gums', description: 'Never place aspirin directly on gums; it causes chemical burns.', category: 'Myth Busting', icon: '💊' },
  { id: 34, title: 'Charcoal Warning', description: 'Charcoal toothpaste can be abrasive and wear down enamel.', category: 'Myth Busting', icon: '⚫' },
];

export function getDailyTip(): Tip {
  const dayOfYear = Math.floor(
    (Date.now() - new Date(new Date().getFullYear(), 0, 0).getTime()) / 86400000
  );
  const dailyTips = tipsList.slice(0, 30);
  return dailyTips[(dayOfYear - 1) % dailyTips.length];
}
