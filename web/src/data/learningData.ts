export interface QuizQuestion {
  question: string;
  options: string[];
  correctIndex: number;
}

export interface Lesson {
  title: string;
  content: string;
}

export interface LearningModule {
  id: string;
  title: string;
  description: string;
  icon: string;
  lessons: Lesson[];
  quiz: QuizQuestion[];
  points: number;
}

export interface LearningCategory {
  id: string;
  title: string;
  icon: string;
  color: string;
  bgColor: string;
  description: string;
  modules: LearningModule[];
}

export const learningCategories: LearningCategory[] = [
  {
    id: 'daily_hygiene',
    title: 'Daily Oral Hygiene',
    icon: '🪥',
    color: '#8774E1',
    bgColor: '#8774E11A',
    description: 'Master the fundamentals of daily oral care',
    modules: [
      {
        id: 'daily_practices',
        title: 'Daily Practices',
        description: 'Essential daily routines for a healthy mouth',
        icon: '✅',
        points: 10,
        lessons: [
          { title: 'Why Daily Hygiene Matters', content: 'Your mouth is home to hundreds of bacterial species. Without daily cleaning, these bacteria form a sticky film called plaque on your teeth. Within 24-48 hours, plaque hardens into tartar—a substance that only a dental professional can remove. Daily brushing and flossing disrupt this process.' },
          { title: 'The Ideal Routine', content: 'Brush twice a day (morning and night) for 2 full minutes each time. Floss once daily, ideally before bedtime. Rinse with an antimicrobial mouthwash. Clean your tongue every morning to remove bacteria that cause bad breath.' },
          { title: 'Common Mistakes to Avoid', content: 'Brushing too hard damages enamel and recedes gums. Skipping nighttime brushing allows bacteria to work undisturbed for hours. Rinsing immediately after brushing washes away protective fluoride. Using the same worn-out toothbrush for too long reduces effectiveness.' },
        ],
        quiz: [
          { question: 'How long should you brush your teeth each time?', options: ['30 seconds', '1 minute', '2 minutes', '5 minutes'], correctIndex: 2 },
          { question: 'When is the most critical time to brush?', options: ['Morning', 'After lunch', 'At night before sleep', 'After every meal'], correctIndex: 2 },
          { question: 'What happens to plaque if not removed within 48 hours?', options: ['It disappears', 'It becomes tartar', 'It becomes saliva', 'Nothing'], correctIndex: 1 },
        ],
      },
      {
        id: 'brushing_technique',
        title: 'Mastering Brushing',
        description: 'The correct brushing technique for maximum effectiveness',
        icon: '🦷',
        points: 10,
        lessons: [
          { title: 'The Bass Technique', content: 'Place your toothbrush at a 45-degree angle to the gumline. Use short, gentle back-and-forth strokes. Brush the outer surfaces, inner surfaces, and chewing surfaces. For the inner front teeth, tilt the brush vertically and make several up-and-down strokes.' },
          { title: 'Choosing the Right Brush', content: 'Always choose a soft-bristled toothbrush. Soft bristles clean effectively without damaging enamel or gums. Replace your toothbrush every 3 months or sooner if bristles are frayed. Electric toothbrushes can be more effective for people with limited dexterity.' },
          { title: 'Toothpaste Selection', content: 'Use fluoride toothpaste—fluoride strengthens enamel and prevents decay. After brushing, spit out excess paste but avoid rinsing with water immediately; this allows fluoride to continue working. For sensitive teeth, use a desensitizing toothpaste containing potassium nitrate or stannous fluoride.' },
        ],
        quiz: [
          { question: 'At what angle should you hold your toothbrush to the gumline?', options: ['90 degrees', '45 degrees', '30 degrees', '15 degrees'], correctIndex: 1 },
          { question: 'What type of bristles should you use?', options: ['Hard', 'Medium', 'Soft', 'Any type'], correctIndex: 2 },
          { question: 'How often should you replace your toothbrush?', options: ['Every month', 'Every 3 months', 'Every 6 months', 'Every year'], correctIndex: 1 },
        ],
      },
      {
        id: 'flossing',
        title: 'Deep Interdental Cleaning',
        description: 'Why flossing is non-negotiable',
        icon: '🧵',
        points: 10,
        lessons: [
          { title: 'Why Brushing Alone is Not Enough', content: 'A toothbrush only cleans about 60% of tooth surfaces. The remaining 40%—the spaces between your teeth—can only be cleaned with floss or interdental tools. Plaque in these areas is the primary cause of gum disease and cavities between teeth.' },
          { title: 'The Correct Flossing Method', content: 'Use about 18 inches of floss, winding most around your middle fingers. Hold a 1-2 inch section taut between your thumbs and forefingers. Guide the floss gently between teeth using a zigzag motion. Curve the floss around each tooth in a "C" shape and slide it gently up and down under the gumline.' },
          { title: 'Types of Floss', content: 'Waxed floss slides easily between tight teeth. Unwaxed floss gives a squeaky-clean feel. Dental tape is wider and good for larger spaces. Floss picks are convenient for on-the-go use. Water flossers are excellent for people with braces or bridges.' },
        ],
        quiz: [
          { question: "What percentage of tooth surfaces can't a toothbrush reach?", options: ['10%', '20%', '40%', '60%'], correctIndex: 2 },
          { question: 'What shape should floss curve around each tooth?', options: ['S shape', 'C shape', 'U shape', 'Z shape'], correctIndex: 1 },
          { question: 'How much floss should you use each time?', options: ['6 inches', '12 inches', '18 inches', '24 inches'], correctIndex: 2 },
        ],
      },
      {
        id: 'tongue_care',
        title: 'Tongue Care',
        description: 'The often-overlooked step in oral hygiene',
        icon: '👅',
        points: 10,
        lessons: [
          { title: "Why Your Tongue Matters", content: 'The tongue has a rough, irregular surface that harbors millions of bacteria. These bacteria, primarily found at the back of the tongue, are a major source of volatile sulfur compounds (VSCs)—the main culprit behind bad breath. Studies show that tongue cleaning can reduce bad breath by up to 70%.' },
          { title: 'How to Clean Your Tongue', content: 'Use a dedicated tongue scraper, which is more effective than a toothbrush. Start from the back of the tongue and pull forward. Rinse the scraper after each stroke. Repeat 5-7 times until the tongue surface looks clean. Do this first thing in the morning before eating or drinking.' },
          { title: 'Signs of an Unhealthy Tongue', content: 'A thick white or yellow coating indicates bacterial overgrowth. A bright red tongue can signal a nutritional deficiency (B12 or folate). Black, hairy-looking tongue is often caused by bacteria overgrowth from antibiotics or poor hygiene. Any persistent lesion or color change should be evaluated by a dentist.' },
        ],
        quiz: [
          { question: 'Where on the tongue do most odor-causing bacteria live?', options: ['Tip', 'Sides', 'Middle', 'Back'], correctIndex: 3 },
          { question: 'By how much can tongue cleaning reduce bad breath?', options: ['10%', '30%', '50%', '70%'], correctIndex: 3 },
          { question: 'When is the best time to clean your tongue?', options: ['Before bed', 'After lunch', 'First thing in the morning', 'Anytime'], correctIndex: 2 },
        ],
      },
    ],
  },
  {
    id: 'prevention_tools',
    title: 'Prevention & Tools',
    icon: '🛡️',
    color: '#4ADE80',
    bgColor: '#4ADE801A',
    description: 'Build a comprehensive oral health toolkit',
    modules: [
      {
        id: 'preventive_habits',
        title: 'Preventive Habits',
        description: 'Lifestyle choices that protect your teeth',
        icon: '💪',
        points: 10,
        lessons: [
          { title: 'Diet and Oral Health', content: 'Every time you eat or drink something sugary, bacteria in your mouth produce acid for 20-30 minutes. Frequent snacking throughout the day means your teeth are under constant acid attack. Limiting snacks and choosing tooth-friendly foods like cheese, yogurt, and crunchy vegetables significantly reduces your cavity risk.' },
          { title: 'Hydration and Saliva', content: "Saliva is your mouth's natural defense system. It neutralizes acids, washes away food particles, and contains minerals that repair early tooth damage. Drinking water throughout the day and chewing sugar-free gum after meals stimulates saliva flow. Avoid mouth breathing and tobacco, which both cause dry mouth." },
          { title: 'Habits to Break', content: 'Nail biting, pencil chewing, and using teeth as tools (opening bottles, tearing packages) can chip or crack teeth. Ice chewing generates extreme pressure that cracks enamel. Grinding teeth (bruxism), often related to stress, wears down enamel and causes jaw pain.' },
        ],
        quiz: [
          { question: 'How long does acid attack last after eating sugar?', options: ['5 minutes', '10 minutes', '20-30 minutes', '1 hour'], correctIndex: 2 },
          { question: 'What is the mouth\'s natural defense system?', options: ['Toothpaste', 'Saliva', 'Mouthwash', 'Water'], correctIndex: 1 },
          { question: 'Which of these is tooth-friendly?', options: ['Chips', 'Soda', 'Cheese', 'Candy'], correctIndex: 2 },
        ],
      },
      {
        id: 'dental_toolkit',
        title: 'Your Dental Toolkit',
        description: 'Understanding and choosing the right tools',
        icon: '🧰',
        points: 10,
        lessons: [
          { title: 'Toothbrush Types', content: 'Manual toothbrushes: Choose soft bristles, replace every 3 months. Electric toothbrushes: More effective at plaque removal, great for people with limited mobility or braces. Children\'s brushes: Smaller head, softer bristles designed for smaller mouths. Travel brushes: Compact, often comes with a cover for hygiene.' },
          { title: 'Toothpaste Guide', content: 'Fluoride toothpaste: Standard protection, essential for cavity prevention. Sensitivity toothpaste: Contains potassium nitrate or arginine to block dentinal tubules. Whitening toothpaste: Contains mild abrasives; use sparingly as it can increase sensitivity. Children\'s toothpaste: Lower fluoride content (1000ppm) for under-6s.' },
          { title: 'Beyond Brushing', content: 'Floss or interdental brushes: Essential for cleaning between teeth. Tongue scraper: Metal scrapers are more durable and effective than plastic ones. Mouthwash: Therapeutic mouthwashes contain active ingredients; cosmetic ones only freshen breath. Water flosser: Uses pressurized water, excellent for implants, braces, and bridges.' },
        ],
        quiz: [
          { question: 'What bristle type should you always choose?', options: ['Hard', 'Medium', 'Soft', 'Any type works'], correctIndex: 2 },
          { question: 'Which toothpaste is essential for cavity prevention?', options: ['Whitening', 'Sensitivity', 'Fluoride', 'Herbal'], correctIndex: 2 },
          { question: 'What is a water flosser excellent for?', options: ['Whitening teeth', 'Braces and implants', 'Strengthening enamel', 'Tongue cleaning'], correctIndex: 1 },
        ],
      },
      {
        id: 'mouthwash',
        title: 'Mouthwash Basics',
        description: 'When and how to use mouthwash effectively',
        icon: '🧴',
        points: 10,
        lessons: [
          { title: 'Types of Mouthwash', content: 'Antiseptic mouthwash (e.g., chlorhexidine): Kills bacteria, reduces gingivitis, used short-term for specific conditions. Fluoride mouthwash: Strengthens enamel, great for cavity-prone individuals. Essential oil mouthwash (e.g., Listerine): Reduces plaque and gingivitis. Cosmetic mouthwash: Only temporarily masks bad breath, no therapeutic benefit.' },
          { title: 'Correct Usage', content: 'Use mouthwash after brushing and flossing, NOT as a replacement. Use the amount specified on the label (usually 20ml). Swish vigorously for 30-60 seconds. Spit out—never swallow. Avoid eating or drinking for 30 minutes after use. Children under 6 should not use mouthwash due to swallowing risk.' },
          { title: 'Common Misconceptions', content: 'Mouthwash does NOT replace brushing or flossing. Strong burning sensation does NOT mean it\'s working better. Alcohol-based mouthwashes can dry out the mouth. Daily use of prescription-strength chlorhexidine can stain teeth with long-term use.' },
        ],
        quiz: [
          { question: 'When should you use mouthwash?', options: ['Before brushing', 'Instead of brushing', 'After brushing and flossing', 'Only in the morning'], correctIndex: 2 },
          { question: 'How long should you swish mouthwash?', options: ['5 seconds', '15 seconds', '30-60 seconds', '2 minutes'], correctIndex: 2 },
          { question: 'What should you avoid after using mouthwash?', options: ['Flossing', 'Eating or drinking for 30 minutes', 'Brushing', 'Sleeping'], correctIndex: 1 },
        ],
      },
      {
        id: 'interdental_tools',
        title: 'Interdental Tools',
        description: 'Advanced tools for cleaning between teeth',
        icon: '🔧',
        points: 10,
        lessons: [
          { title: 'Interdental Brushes', content: 'These small, Christmas-tree-shaped brushes are inserted between teeth to clean the spaces. They are particularly effective for people with larger gaps between teeth, dental implants, or fixed bridges. They come in various sizes (0.4mm to 1.5mm); your dentist or hygienist can advise on the right size for you.' },
          { title: 'Floss Picks and Threaders', content: 'Floss picks are pre-threaded single-use flossers that are convenient for on-the-go use. Floss threaders allow you to thread floss under bridges and between braces wires. These tools make flossing accessible for people who struggle with traditional floss.' },
          { title: 'Water Flossers', content: 'Water flossers use a stream of pressurized water to remove food and plaque between teeth and below the gumline. They are not a complete replacement for string floss but are an excellent supplement, especially for those with braces, implants, or arthritis that makes string flossing difficult.' },
        ],
        quiz: [
          { question: 'What shape are interdental brushes?', options: ['Flat', 'Round', 'Christmas-tree shaped', 'Square'], correctIndex: 2 },
          { question: 'What do floss threaders help with?', options: ['Whitening', 'Threading floss under bridges and braces', 'Tongue cleaning', 'Massaging gums'], correctIndex: 1 },
          { question: 'Are water flossers a complete replacement for string floss?', options: ['Yes', 'No, they are a supplement', 'Only for adults', 'Only for children'], correctIndex: 1 },
        ],
      },
    ],
  },
  {
    id: 'dental_conditions',
    title: 'Common Dental Conditions',
    icon: '🔬',
    color: '#FF6B6B',
    bgColor: '#FF6B6B1A',
    description: "Understand your mouth's warning signs",
    modules: [
      {
        id: 'warning_signs',
        title: "Your Mouth's Warning Signs",
        description: 'Recognizing early signs of dental problems',
        icon: '⚠️',
        points: 10,
        lessons: [
          { title: 'Pain and Sensitivity', content: 'Dental pain is always a signal that something needs attention. Sharp pain triggered by temperature (hot/cold) or sweetness suggests tooth decay or exposed roots. Throbbing, constant pain often indicates infection or abscess. Pain when biting can signal a cracked tooth or failing filling.' },
          { title: 'Gum Warning Signs', content: 'Healthy gums are firm, pale pink, and don\'t bleed. Red, swollen, or bleeding gums are signs of gingivitis. Gums that pull away from teeth (recession) expose vulnerable root surfaces. Pus between teeth and gums signals active infection requiring immediate attention.' },
          { title: 'Visual Warning Signs', content: 'White spots on teeth can indicate early decay (pre-cavity stage). Brown or black spots may be active decay or old staining. White patches (leukoplakia) or red patches (erythroplakia) on soft tissues can be serious. Any sore that doesn\'t heal within 14 days needs professional evaluation.' },
        ],
        quiz: [
          { question: 'What does throbbing, constant dental pain usually indicate?', options: ['Normal sensitivity', 'Infection or abscess', 'Teeth whitening effect', 'Vitamin deficiency'], correctIndex: 1 },
          { question: 'What color are healthy gums?', options: ['Bright red', 'Dark purple', 'Pale pink', 'White'], correctIndex: 2 },
          { question: 'How long before an oral sore needs professional evaluation?', options: ['3 days', '7 days', '14 days', '30 days'], correctIndex: 2 },
        ],
      },
      {
        id: 'decay_stages',
        title: 'The 3 Stages of Decay',
        description: 'Understanding how cavities progress',
        icon: '📊',
        points: 10,
        lessons: [
          { title: 'Stage 1: Enamel Decay', content: "The first stage begins when acids erode the outer enamel layer. You may notice white spots (demineralization) on the tooth surface. At this stage, the decay can often be REVERSED with fluoride treatments, improved hygiene, and diet changes. This is why early detection at regular check-ups is so valuable." },
          { title: 'Stage 2: Dentin Decay', content: "Once decay penetrates through enamel into the dentin (softer inner layer), a cavity has officially formed. Dentin is less hard than enamel, so decay spreads faster. You'll likely experience sensitivity to sweet, hot, or cold foods. A dental filling is now required to stop the progression." },
          { title: 'Stage 3: Pulp Involvement', content: "The pulp contains nerves and blood vessels. When decay reaches this stage, you'll experience severe, often spontaneous tooth pain. Treatment now requires a root canal procedure to remove the infected pulp, followed by a crown. Prevention at earlier stages is far simpler and less expensive." },
        ],
        quiz: [
          { question: 'At what stage can decay potentially be reversed?', options: ['Stage 1: Enamel decay', 'Stage 2: Dentin decay', 'Stage 3: Pulp involvement', 'No stage is reversible'], correctIndex: 0 },
          { question: 'What does decay spreading into dentin require?', options: ['More brushing', 'Fluoride treatment', 'A dental filling', 'Root canal'], correctIndex: 2 },
          { question: 'What treatment is required when decay reaches the pulp?', options: ['Fluoride treatment', 'Dental filling', 'Root canal', 'Extraction only'], correctIndex: 2 },
        ],
      },
      {
        id: 'gum_stages',
        title: 'Gum Disease Stages',
        description: 'From gingivitis to advanced periodontitis',
        icon: '🦠',
        points: 10,
        lessons: [
          { title: 'Gingivitis: The Reversible Stage', content: 'Gingivitis is inflammation confined to the gums. Signs: red, swollen gums that bleed during brushing. The good news: gingivitis is completely reversible with professional cleaning and improved home care. The plaque and tartar causing the inflammation are removed, and gums return to health.' },
          { title: 'Early Periodontitis', content: 'When gingivitis is ignored, infection spreads below the gumline. Pockets form between teeth and gums (>3mm). Early bone loss begins. Treatment requires a "deep cleaning" (scaling and root planing) to remove bacteria from below the gumline. Some bone loss may be permanent.' },
          { title: 'Advanced Periodontitis', content: 'Significant bone and tissue loss makes teeth lose their support. Teeth may become loose and shift. Eating becomes difficult. Treatment may include surgery to access deep pockets and regenerate bone. In severe cases, teeth cannot be saved and must be extracted. Prevention is always the best approach.' },
        ],
        quiz: [
          { question: 'Which stage of gum disease is completely reversible?', options: ['Early Periodontitis', 'Gingivitis', 'Advanced Periodontitis', 'None'], correctIndex: 1 },
          { question: 'What size pocket indicates early periodontitis?', options: ['Less than 1mm', 'Greater than 3mm', 'Exactly 2mm', 'Greater than 10mm'], correctIndex: 1 },
          { question: 'What procedure treats early periodontitis?', options: ['Regular cleaning', 'Scaling and root planing', 'Surgery only', 'Extraction'], correctIndex: 1 },
        ],
      },
      {
        id: 'progression',
        title: 'Understanding Progression',
        description: 'Why early treatment always wins',
        icon: '📈',
        points: 10,
        lessons: [
          { title: 'The Cost of Delay', content: 'A small cavity detected at a check-up costs a fraction to fill compared to a tooth that has been allowed to decay to the point of needing a crown or extraction. Similarly, gingivitis treated with one professional cleaning is far less complex than periodontitis requiring surgery. Time is always working against untreated dental disease.' },
          { title: 'The Domino Effect', content: 'Dental problems rarely stay isolated. A decayed tooth can abscess and spread infection to adjacent teeth and bone. A missing tooth causes neighboring teeth to shift and tilt, altering the bite and creating cleaning difficulties. Gum disease bacteria have been linked to cardiovascular disease, diabetes complications, and premature birth.' },
          { title: 'The Importance of Check-ups', content: 'Many dental conditions, including early decay and oral cancer, have no symptoms in their early stages. Regular professional examinations and X-rays can detect issues before they cause pain or visible symptoms. The standard recommendation is a check-up every 6 months, though higher-risk individuals may need more frequent visits.' },
        ],
        quiz: [
          { question: 'What is the standard recommendation for dental check-ups?', options: ['Every month', 'Every 3 months', 'Every 6 months', 'Every year'], correctIndex: 2 },
          { question: 'What can happen to neighboring teeth when one is lost?', options: ['They strengthen', 'They shift and tilt', 'They stay the same', 'They become whiter'], correctIndex: 1 },
          { question: 'Which condition has no symptoms in early stages?', options: ['Toothache', 'Swelling', 'Oral cancer', 'Bleeding gums'], correctIndex: 2 },
        ],
      },
    ],
  },
  {
    id: 'specialized_care',
    title: 'Specialized Care',
    icon: '🏥',
    color: '#FFB347',
    bgColor: '#FFB3471A',
    description: 'Tailored oral care for specific needs',
    modules: [
      {
        id: 'children',
        title: 'Children\'s Oral Health',
        description: 'Building healthy habits from the start',
        icon: '👶',
        points: 10,
        lessons: [
          { title: 'From Birth to First Tooth', content: "Even before teeth appear, you can wipe your baby's gums with a soft, damp cloth after feedings. The first tooth usually appears around 6 months. See a dentist within 6 months of the first tooth appearing, or by age 1. Avoid putting a baby to sleep with a bottle of milk or juice—this causes 'baby bottle tooth decay.'" },
          { title: 'Supervising Brushing', content: 'Children lack the coordination to brush effectively until about age 7-8. Use a grain-of-rice-sized amount of fluoride toothpaste for under-3s, and a pea-sized amount for 3-6 year olds. Make brushing fun: use a timer, play music, or use apps. Supervise and check their brushing until you are confident they can do it well.' },
          { title: 'Diet for Healthy Teeth', content: 'Limit juice to 4-6 oz per day and dilute it with water. Avoid sticky, chewy candies that cling to teeth. Encourage water as the main drink between meals. If giving sugary treats, do so with meals rather than as snacks between meals. Cheese, milk, and yogurt are excellent tooth-friendly snacks.' },
        ],
        quiz: [
          { question: 'When should a child first see a dentist?', options: ['Age 3', 'Within 6 months of first tooth or by age 1', 'Age 5', 'When they get all baby teeth'], correctIndex: 1 },
          { question: 'Until what age should you supervise brushing?', options: ['Age 5', 'Age 7-8', 'Age 10', 'Age 12'], correctIndex: 1 },
          { question: 'How much toothpaste for a child under 3?', options: ['Full brush', 'Pea-sized', 'Grain-of-rice-sized', 'None'], correctIndex: 2 },
        ],
      },
      {
        id: 'elderly',
        title: 'Elderly Oral Health',
        description: 'Maintaining oral health as you age',
        icon: '👴',
        points: 10,
        lessons: [
          { title: 'Age-Related Changes', content: "As we age, gums naturally recede, exposing more root surface. Roots are not protected by enamel and are more susceptible to cavities. Saliva production often decreases with age, increasing decay and infection risk. Many medications common in older adults cause dry mouth as a side effect. Arthritis can make proper brushing and flossing challenging." },
          { title: 'Caring for Natural Teeth', content: 'If you have natural teeth in old age, protecting them is worth the effort. Use a soft toothbrush and consider an electric model for easier use. Apply fluoride gel or varnish (prescribed by a dentist) to protect exposed roots. Stay hydrated and use saliva substitutes if you suffer from dry mouth.' },
          { title: 'Denture Care', content: 'Dentures should be removed and cleaned daily with a soft brush and denture cleaner. Soak them overnight in denture solution or water. Clean your gums, tongue, and palate daily with a soft brush even without teeth. See your dentist annually for denture adjustments, as jaw shape changes over time. Ill-fitting dentures cause sores and difficulty eating.' },
        ],
        quiz: [
          { question: 'Why are exposed roots more susceptible to decay?', options: ['They are bigger', 'They are not protected by enamel', 'They are softer', 'They attract more bacteria'], correctIndex: 1 },
          { question: 'What is a common side effect of many elderly medications?', options: ['Tooth sensitivity', 'Dry mouth', 'Gum disease', 'Tooth darkening'], correctIndex: 1 },
          { question: 'How often should dentures be cleaned?', options: ['Weekly', 'Every 3 days', 'Daily', 'Monthly'], correctIndex: 2 },
        ],
      },
      {
        id: 'orthodontic',
        title: 'Orthodontic Patients',
        description: 'Oral care with braces or aligners',
        icon: '🦷',
        points: 10,
        lessons: [
          { title: 'Oral Hygiene with Braces', content: 'Braces create many new places for plaque to hide. Brush after every meal and snack—not just twice a day. Use a soft brush and angle it at 45 degrees to clean under the wires. Use floss threaders or orthodontic flossers to get between teeth. An electric toothbrush with an orthodontic head is highly recommended.' },
          { title: 'Foods to Avoid with Braces', content: 'Hard foods (ice, nuts, hard candy) can break brackets. Sticky foods (caramel, gum, chewy candy) pull wires and brackets off. Crunchy foods (raw carrots, apples) should be cut into small pieces. Avoid biting into sandwiches, pizza, or burgers without cutting them up. Follow your orthodontist\'s specific guidance.' },
          { title: 'Aligner Care', content: "Aligners (like Invisalign) must be removed before eating or drinking anything other than water. Clean your teeth thoroughly before reinserting aligners—food and bacteria trapped under aligners dramatically increase decay risk. Clean aligners with a soft brush and lukewarm water; don't use hot water (warps plastic). Wear aligners for 20-22 hours a day for effective treatment." },
        ],
        quiz: [
          { question: 'How often should you brush with braces?', options: ['Once a day', 'Twice a day', 'After every meal', 'Weekly'], correctIndex: 2 },
          { question: 'Which food should you avoid with braces?', options: ['Yogurt', 'Soft bread', 'Caramel candy', 'Bananas'], correctIndex: 2 },
          { question: 'How many hours per day should aligners be worn?', options: ['8-10 hours', '12-14 hours', '16-18 hours', '20-22 hours'], correctIndex: 3 },
        ],
      },
      {
        id: 'pregnancy',
        title: 'Pregnancy & Oral Health',
        description: "Protecting mother and baby's oral health",
        icon: '🤰',
        points: 10,
        lessons: [
          { title: 'How Pregnancy Affects Oral Health', content: 'Hormonal changes during pregnancy cause increased blood flow to gums, making them more sensitive and prone to swelling and bleeding—a condition called "pregnancy gingivitis." Morning sickness exposes teeth to stomach acid, which erodes enamel. Changes in eating habits (more frequent small meals) can increase cavity risk.' },
          { title: 'Safe Dental Care During Pregnancy', content: 'Routine dental cleaning and examinations are safe and recommended during pregnancy. The second trimester is generally the most comfortable time for dental treatment. Elective procedures are best postponed until after delivery. Emergency treatment should never be delayed. Local anesthesia (without epinephrine if possible) is safe.' },
          { title: 'Protecting Your Baby', content: "A mother's oral health directly affects her baby. Severe gum disease has been linked to premature birth and low birth weight. Cavity-causing bacteria can be transmitted from mother to baby through kissing, sharing utensils, or pre-tasting food. Dental care during pregnancy is an investment in both mother's and baby's health." },
        ],
        quiz: [
          { question: 'What is "pregnancy gingivitis" caused by?', options: ['Poor diet', 'Hormonal changes increasing blood flow to gums', 'Calcium deficiency', 'Stress'], correctIndex: 1 },
          { question: 'When is the best trimester for dental treatment?', options: ['First', 'Second', 'Third', 'After delivery only'], correctIndex: 1 },
          { question: 'How can cavity-causing bacteria be transmitted to babies?', options: ['Breathing same air', 'Through breast milk only', 'Sharing utensils or kissing', 'Touch'], correctIndex: 2 },
        ],
      },
    ],
  },
  {
    id: 'dental_procedures',
    title: 'Dental Procedures',
    icon: '⚕️',
    color: '#C084FC',
    bgColor: '#C084FC1A',
    description: 'What to expect at the dentist',
    modules: [
      {
        id: 'checkup',
        title: 'Dental Check-up',
        description: 'What happens during a routine visit',
        icon: '🔍',
        points: 10,
        lessons: [
          { title: 'What to Expect', content: 'A routine check-up includes a comprehensive examination of your teeth, gums, and soft tissues. The dentist checks for cavities, gum disease, oral cancer signs, and existing restorations. X-rays are typically taken every 1-2 years to detect issues invisible to the naked eye, like decay between teeth or bone loss.' },
          { title: 'Professional Cleaning', content: 'A dental hygienist uses specialized instruments to remove plaque and tartar (calculus) that cannot be removed by brushing and flossing alone. The cleaning includes scaling (removing tartar), polishing (removing surface stains), and flossing. This process is called prophylaxis.' },
          { title: 'Frequency and Importance', content: 'Most people benefit from check-ups every 6 months. High-risk individuals (smokers, diabetics, people prone to cavities or gum disease) may need to be seen every 3-4 months. Early detection at routine check-ups can prevent minor issues from becoming major, costly problems. Never skip check-ups even when you feel no pain.' },
        ],
        quiz: [
          { question: 'How often are dental X-rays typically taken?', options: ['Every visit', 'Every 1-2 years', 'Every 5 years', 'Only when there is pain'], correctIndex: 1 },
          { question: 'What is professional tooth cleaning called?', options: ['Extraction', 'Prophylaxis', 'Root canal', 'Scaling only'], correctIndex: 1 },
          { question: 'How often should high-risk individuals have check-ups?', options: ['Every 6 months', 'Once a year', 'Every 3-4 months', 'Every 2 years'], correctIndex: 2 },
        ],
      },
      {
        id: 'scaling',
        title: 'Scaling (Cleaning)',
        description: 'Professional plaque and tartar removal',
        icon: '✨',
        points: 10,
        lessons: [
          { title: 'What is Scaling?', content: "Scaling (also called debridement or prophylaxis) is the removal of plaque and tartar from tooth surfaces and below the gumline. Unlike brushing at home, scaling uses specialized instruments—ultrasonic scalers (vibrating tips) and hand instruments—to break up and remove hardened calculus that cannot be dislodged by a toothbrush." },
          { title: 'Deep Cleaning (SRP)', content: 'When gum disease is present, a more intensive procedure called Scaling and Root Planing (SRP) is performed. In addition to removing tartar above and below the gumline, the root surfaces are smoothed (planed) to help gums reattach to the teeth. Local anesthesia is used for comfort. Multiple appointments may be needed for the full mouth.' },
          { title: 'After Scaling', content: 'Some sensitivity and soreness is normal for a few days. Gums may look and feel different as inflammation resolves. Keep the area clean with gentle brushing and warm salt water rinses. Bleeding should decrease significantly over the following 1-2 weeks. Follow-up appointments assess healing and determine if further treatment is needed.' },
        ],
        quiz: [
          { question: 'What does scaling remove?', options: ['Only surface stains', 'Only plaque', 'Plaque and tartar', 'Tooth enamel'], correctIndex: 2 },
          { question: 'What does SRP stand for?', options: ['Surface Removal Procedure', 'Scaling and Root Planing', 'Sensitive Root Protection', 'Standard Rinse Protocol'], correctIndex: 1 },
          { question: 'How long may gum soreness last after scaling?', options: ['A few hours', 'A few days', 'A few weeks', 'A few months'], correctIndex: 1 },
        ],
      },
      {
        id: 'fillings',
        title: 'Dental Fillings',
        description: 'Restoring teeth damaged by decay',
        icon: '🔧',
        points: 10,
        lessons: [
          { title: 'Types of Fillings', content: 'Composite (tooth-colored): Most commonly used today; bonds to tooth structure; good for visible teeth. Amalgam (silver): Very durable, good for back teeth; less aesthetic; being phased out in some countries. Glass ionomer: Releases fluoride; often used for children or root surfaces. Gold/Porcelain inlays: Custom-made, very durable, used for large restorations.' },
          { title: 'The Filling Procedure', content: 'The area is numbed with local anesthesia. The decayed tooth material is removed with a drill. The cavity is shaped, cleaned, and dried. For composite: layers of resin are applied and hardened with a blue curing light. The filling is shaped and polished to match your bite. Most fillings are completed in one appointment.' },
          { title: 'Caring for Fillings', content: 'Avoid very hot or cold foods for 24 hours after a new composite filling. Slight sensitivity is normal for a few weeks. Maintain excellent oral hygiene—cavities can still form around filling margins. Fillings last 5-15 years depending on type and care. Tell your dentist if you feel the filling is "high" (interferes with bite).' },
        ],
        quiz: [
          { question: 'What is the most commonly used filling type today?', options: ['Gold', 'Amalgam', 'Composite (tooth-colored)', 'Glass ionomer'], correctIndex: 2 },
          { question: 'What hardens composite resin?', options: ['Air drying', 'Blue curing light', 'Heat', 'Water'], correctIndex: 1 },
          { question: 'How long do fillings typically last?', options: ['1-2 years', '3-4 years', '5-15 years', 'A lifetime'], correctIndex: 2 },
        ],
      },
      {
        id: 'root_canal',
        title: 'Root Canal Treatment',
        description: 'Saving a severely infected tooth',
        icon: '💉',
        points: 10,
        lessons: [
          { title: 'When Is It Needed?', content: "Root canal treatment is needed when the dental pulp (the living tissue inside the tooth containing nerves and blood vessels) becomes infected or dies. This can happen due to deep decay, repeated dental procedures, a cracked tooth, or trauma. Without treatment, the infection spreads to surrounding bone, causing an abscess. A root canal saves the tooth from extraction." },
          { title: 'The Procedure Demystified', content: "With modern techniques and anesthesia, root canal treatment is no more uncomfortable than getting a filling—the area is fully numbed before any work begins. The procedure involves: (1) Making a small opening in the tooth crown, (2) Removing infected/dead pulp tissue, (3) Cleaning and shaping the root canals, (4) Filling the canals with a rubber material (gutta-percha), (5) Sealing the tooth. A crown is usually placed afterward." },
          { title: 'Recovery and Success', content: 'Some soreness is expected for a few days after treatment—manage with over-the-counter pain relievers. Avoid chewing on the treated tooth until a crown is placed. Root canal treated teeth are brittle and MUST have a crown to protect them from fracture. Success rates are over 95%. Properly treated teeth can last a lifetime.' },
        ],
        quiz: [
          { question: 'What does root canal treatment do?', options: ['Removes the tooth', 'Saves infected tooth by removing pulp', 'Whitens the tooth', 'Repairs the enamel'], correctIndex: 1 },
          { question: 'What material is used to fill root canals?', options: ['Composite resin', 'Amalgam', 'Gutta-percha', 'Gold'], correctIndex: 2 },
          { question: 'What must be placed after root canal treatment?', options: ['A filling', 'A crown', 'A bridge', 'Nothing'], correctIndex: 1 },
        ],
      },
      {
        id: 'extraction',
        title: 'Tooth Extraction',
        description: 'When and how teeth are removed',
        icon: '🦷',
        points: 10,
        lessons: [
          { title: 'When Extraction is Necessary', content: 'Extractions are performed when a tooth cannot be saved: severe decay beyond repair, advanced periodontal disease with significant bone loss, a cracked tooth below the gumline, impacted wisdom teeth causing problems, or orthodontic treatment requiring space. Modern dentistry aims to save teeth whenever possible.' },
          { title: 'Simple vs Surgical Extraction', content: 'Simple extraction: Used for visible teeth. Local anesthesia is applied, and the tooth is loosened with an instrument (elevator) and removed with forceps. Surgical extraction: Required for impacted teeth, broken teeth below the gumline, or complex cases. A small incision is made in the gum. Stitches may be needed.' },
          { title: 'Post-Extraction Care', content: 'Bite on gauze for 30-45 minutes to form a blood clot. Avoid smoking, spitting, or using straws for 24 hours (dislodges clot causing "dry socket"). Apply ice packs for 20 minutes to reduce swelling. Eat soft foods for a few days. Keep the area clean by gentle rinsing with warm salt water after 24 hours. Call your dentist if pain increases after day 2-3.' },
        ],
        quiz: [
          { question: 'What is "dry socket"?', options: ['Infection', 'When blood clot is dislodged after extraction', 'A type of filling', 'Normal healing'], correctIndex: 1 },
          { question: 'How long should you bite on gauze after extraction?', options: ['5-10 minutes', '15-20 minutes', '30-45 minutes', '2 hours'], correctIndex: 2 },
          { question: 'What should you avoid for 24 hours after extraction?', options: ['Brushing nearby teeth', 'Using straws', 'Drinking water', 'Sleeping'], correctIndex: 1 },
        ],
      },
      {
        id: 'wisdom_tooth',
        title: 'Wisdom Tooth Problems',
        description: 'Understanding impacted wisdom teeth',
        icon: '🧠',
        points: 10,
        lessons: [
          { title: 'What Are Wisdom Teeth?', content: "Wisdom teeth (third molars) are the last teeth to erupt, typically between ages 17-25. Most people have four wisdom teeth, but some have fewer or none. Due to modern jaw size, there is often insufficient space for wisdom teeth to erupt properly. When there is no room, they become 'impacted'—trapped fully or partially in the jaw." },
          { title: 'Problems Caused by Wisdom Teeth', content: "Impacted wisdom teeth can cause: pain and swelling in the back of the mouth, infection in the gum flap over a partially erupted tooth ('pericoronitis'), damage to the adjacent second molar, cyst formation around the impacted tooth (which can destroy bone and adjacent teeth), and crowding affecting orthodontic treatment results." },
          { title: 'Treatment Options', content: "Not all wisdom teeth need removal. If they are fully erupted, properly aligned, and healthy, they can be kept. Monitoring with X-rays is still recommended. If they are causing or likely to cause problems, removal is recommended—ideally in your early 20s when roots are shorter and bone is more flexible. Recovery is generally 3-7 days. Ice, soft foods, and prescribed medications help manage recovery." },
        ],
        quiz: [
          { question: 'At what age do wisdom teeth typically erupt?', options: ['10-13', '14-16', '17-25', '26-30'], correctIndex: 2 },
          { question: "What is 'pericoronitis'?", options: ['A type of cavity', 'Infection under the gum flap of partially erupted wisdom tooth', 'Root canal', 'Jaw joint disorder'], correctIndex: 1 },
          { question: 'How long is wisdom tooth recovery typically?', options: ['1 day', '3-7 days', '2 weeks', '1 month'], correctIndex: 1 },
        ],
      },
    ],
  },
  {
    id: 'systemic_health',
    title: 'Systemic Health & Oral Health',
    icon: '❤️',
    color: '#F472B6',
    bgColor: '#F472B61A',
    description: 'How oral health connects to overall wellness',
    modules: [
      {
        id: 'mouth_body',
        title: 'Mouth-Body Connection',
        description: 'The link between oral and systemic health',
        icon: '🔗',
        points: 10,
        lessons: [
          { title: 'The Oral-Systemic Link', content: "Your mouth is the entry point to your body. The same bacteria that cause gum disease can enter your bloodstream through inflamed gum tissue. Once in circulation, these bacteria can travel to other organs and trigger inflammatory responses throughout the body. Research has increasingly linked poor oral health to serious systemic conditions." },
          { title: 'Conditions Linked to Oral Health', content: "Cardiovascular disease: Oral bacteria may contribute to arterial plaque formation. Diabetes: A two-way relationship—diabetes worsens gum disease, and gum disease makes blood sugar harder to control. Respiratory infections: Bacteria from the mouth can be inhaled into the lungs. Adverse pregnancy outcomes: Severe gum disease linked to premature birth and low birth weight. Alzheimer's disease: Some studies suggest links between oral bacteria and cognitive decline." },
          { title: 'Inflammation: The Common Thread', content: 'Chronic inflammation is the underlying mechanism connecting oral and systemic disease. Gum disease is essentially a chronic inflammatory condition. The inflammatory mediators (cytokines) released during gum disease enter the bloodstream and can affect inflammation levels throughout the body. Treating gum disease has been shown to improve markers of systemic inflammation.' },
        ],
        quiz: [
          { question: 'How can oral bacteria reach other organs?', options: ['Through saliva', 'Through the bloodstream', 'Through breathing', 'They cannot travel'], correctIndex: 1 },
          { question: 'What is the common thread linking oral and systemic disease?', options: ['Bacteria', 'Sugar', 'Chronic inflammation', 'Genetics'], correctIndex: 2 },
          { question: 'Which condition has a two-way relationship with gum disease?', options: ['Arthritis', 'Diabetes', 'Asthma', 'Thyroid disease'], correctIndex: 1 },
        ],
      },
      {
        id: 'diabetes_oral',
        title: 'Diabetes & Oral Health',
        description: 'Managing the diabetes-gum disease connection',
        icon: '🩺',
        points: 10,
        lessons: [
          { title: 'The Two-Way Relationship', content: 'Diabetes and gum disease have a bidirectional relationship—each makes the other worse. Poorly controlled diabetes impairs the immune system, reducing the ability to fight oral infections. Higher blood sugar creates an environment where bacteria thrive. Conversely, the inflammation of gum disease raises blood sugar, making diabetes harder to control.' },
          { title: 'Warning Signs in Diabetic Patients', content: 'Diabetic individuals should watch for: more frequent and severe gum infections, slower healing after dental procedures, thrush (oral yeast infection) especially if blood sugar is poorly controlled, dry mouth (a side effect of some diabetes medications), burning tongue or altered taste sensation.' },
          { title: 'Management Tips', content: 'Control blood sugar as consistently as possible—this directly reduces oral infection risk. Inform your dentist about your diabetes diagnosis and medications. Schedule dental check-ups at least every 6 months (or more frequently if gum disease is present). Maintain meticulous home oral hygiene. Dry mouth management: drink water, chew sugar-free gum, use saliva substitutes if needed.' },
        ],
        quiz: [
          { question: 'How does poorly controlled diabetes affect oral health?', options: ['Has no effect', 'Reduces bacteria', 'Impairs immune system increasing infection risk', 'Strengthens teeth'], correctIndex: 2 },
          { question: 'How does gum disease affect blood sugar?', options: ['Lowers it', 'Has no effect', 'Raises it through inflammation', 'Stabilizes it'], correctIndex: 2 },
          { question: 'How often should diabetic patients have dental check-ups?', options: ['Once every 2 years', 'Once a year', 'At least every 6 months', 'Only when in pain'], correctIndex: 2 },
        ],
      },
      {
        id: 'heart_health',
        title: 'Heart Health Link',
        description: 'Oral health and cardiovascular disease',
        icon: '❤️',
        points: 10,
        lessons: [
          { title: 'The Research', content: 'Multiple large studies have found that people with gum disease are 2-3 times more likely to suffer a heart attack or stroke. While this does not prove causation (it may partly reflect shared risk factors like smoking), the association is strong enough that cardiologists and dentists now take it seriously. Oral bacteria have been found in atherosclerotic plaques in arteries.' },
          { title: 'Mechanisms Proposed', content: 'Several mechanisms have been proposed: Direct infection: Oral bacteria enter the bloodstream and adhere to damaged heart valves or arterial walls (endocarditis risk). Inflammatory pathway: Gum disease elevates systemic inflammatory markers (CRP, fibrinogen) that promote arterial inflammation and clot formation. Indirect effects: Shared risk factors like smoking, poor diet, and socioeconomic factors influence both conditions.' },
          { title: 'Practical Takeaways', content: 'If you have a history of heart disease, inform your dentist at every appointment. People with artificial heart valves or certain congenital heart conditions may need antibiotic premedication before dental procedures. Treating gum disease has been shown in some studies to modestly improve cardiovascular markers. View dental health as part of your overall cardiovascular risk management.' },
        ],
        quiz: [
          { question: 'How much more likely are people with gum disease to have a heart attack?', options: ['No increased risk', '2-3 times more likely', '10 times more likely', 'Half as likely'], correctIndex: 1 },
          { question: 'What is endocarditis?', options: ['Gum infection', 'Tooth decay', 'Infection of heart valves', 'Jaw pain'], correctIndex: 2 },
          { question: 'Who may need antibiotic premedication before dental procedures?', options: ['Diabetics', 'People with artificial heart valves', 'Pregnant women', 'Children'], correctIndex: 1 },
        ],
      },
      {
        id: 'nutrition',
        title: 'Nutrition for Oral Health',
        description: 'Foods that protect or damage teeth',
        icon: '🥗',
        points: 10,
        lessons: [
          { title: 'Foods That Protect Teeth', content: "Calcium-rich foods (dairy, leafy greens, almonds): Strengthen enamel. Phosphorus-rich foods (meat, fish, eggs): Work with calcium to rebuild enamel. Crunchy fruits and vegetables (apples, carrots, celery): Stimulate saliva and physically scrub teeth. Green and black teas: Contain polyphenols that suppress harmful bacteria. Water: The best drink for teeth—neutralizes acid and washes away food." },
          { title: 'Foods That Damage Teeth', content: "Sugary foods and drinks: Feed acid-producing bacteria. Acidic foods (citrus, vinegar, carbonated drinks): Directly erode enamel. Sticky, chewy foods (dried fruit, gummy candy): Cling to teeth and prolong acid exposure. Starchy snacks (chips, crackers): Break down into sugars that bacteria ferment. Alcohol: Reduces saliva and can contribute to oral cancer risk." },
          { title: 'Smart Eating Habits', content: "Limit snacking frequency—each snack triggers a 20-30 minute acid attack. If you do snack, choose tooth-friendly options. End meals with dairy or alkaline foods to help neutralize acid. Drink water after meals. Wait 30 minutes after eating acidic foods before brushing (brushing immediately can spread weakened enamel). Avoid sipping sugary or acidic drinks slowly throughout the day." },
        ],
        quiz: [
          { question: 'Which mineral is essential for strengthening enamel?', options: ['Iron', 'Potassium', 'Calcium', 'Magnesium'], correctIndex: 2 },
          { question: 'What is the best drink for your teeth?', options: ['Milk', 'Green tea', 'Water', 'Coconut water'], correctIndex: 2 },
          { question: 'How long should you wait to brush after eating acidic foods?', options: ['Immediately', '5 minutes', '30 minutes', '2 hours'], correctIndex: 2 },
        ],
      },
    ],
  },
];
