export interface Disease {
  id: string;
  name: string;
  icon: string;
  color: string;
  bgColor: string;
  whatIsHappening: string;
  whatPeopleNotice: string;
  whyItHappens: string;
  whyNotIgnore: string;
  whenToSeeDentist: string;
}

export const diseases: Disease[] = [
  {
    id: 'gingivitis',
    name: 'Gingivitis',
    icon: '🩸',
    color: '#22C55E',
    bgColor: '#22C55E1A',
    whatIsHappening:
      'Gingivitis is the earliest and only truly reversible stage of gum disease. It occurs when a sticky, colorless film of bacteria called plaque accumulates on the teeth and produces toxins that irritate the gum tissue. If not removed by daily brushing and flossing, plaque hardens into tartar (calculus), which provides a shield for more bacteria and can only be removed by a professional dental cleaning.',
    whatPeopleNotice:
      'The most common signs are gums that look red and puffy instead of a healthy pale pink. You may see blood on your toothbrush or in the sink when flossing. Persistent bad breath or a metallic taste in the mouth are also early indicators. Crucially, gingivitis is often painless, which is why many people ignore it until it progresses.',
    whyItHappens:
      'The primary cause is poor oral hygiene leading to plaque buildup. Other contributing factors include hormonal changes (like pregnancy or puberty), certain medications that cause dry mouth, smoking, and systemic diseases like diabetes that reduce the body\'s ability to fight infection.',
    whyNotIgnore:
      "While gingivitis is reversible, ignoring it allows the infection to spread below the gumline. This leads to periodontitis, where the body's immune system begins to break down the bone and connective tissue that hold teeth in place, eventually leading to loose teeth and tooth loss.",
    whenToSeeDentist:
      'Schedule a visit if you notice your gums bleeding during routine cleaning, or if you see visible recession where the gum line appears to be pulling away from the teeth.',
  },
  {
    id: 'cavities',
    name: 'Cavities (Tooth Decay)',
    icon: '⚠️',
    color: '#8B5CF6',
    bgColor: '#8B5CF61A',
    whatIsHappening:
      "Tooth decay is a process where the hard mineral structure of the tooth (enamel) is destroyed over time. It starts with demineralization, where acids produced by mouth bacteria dissolve surface minerals. If this process continues, it creates a hole or 'cavity' that penetrates the enamel and reaches the softer dentin layer underneath.",
    whatPeopleNotice:
      'In the early stages, you might see white spots on the teeth. As it progresses, you may experience sharp pain when eating sweet, hot, or cold foods. Visible pits, black/brown staining on any tooth surface, and persistent food trapping between teeth are common signs that a cavity has formed.',
    whyItHappens:
      "It's a combination of factors: bacteria in your mouth (like Mutans streptococci) feed on dietary sugars and starches to create acid. Frequent snacking on sugary foods, sipping acidic drinks, and inadequate fluoride exposure make the enamel more vulnerable to these acid attacks.",
    whyNotIgnore:
      "Decay never stops on its own. Once it reaches the dentin, it spreads much faster toward the tooth's nerve (pulp). This leads to agonizing toothaches, abscesses (infections at the root), and can eventually require complex root canal therapy or total tooth extraction.",
    whenToSeeDentist:
      'See a dentist immediately if you have a lingering toothache, sharp sensitivity, or if you can feel a hole or rough edge with your tongue.',
  },
  {
    id: 'bad_breath',
    name: 'Bad Breath (Halitosis)',
    icon: '💨',
    color: '#22C55E',
    bgColor: '#22C55E1A',
    whatIsHappening:
      'Chronic bad breath is typically caused by the breakdown of proteins by anaerobic bacteria in the mouth. These bacteria live in areas where oxygen is low, such as the deep crevices of the tongue, under the gumline, and between teeth, producing foul-smelling volatile sulfur compounds (VSCs).',
    whatPeopleNotice:
      'A persistent unpleasant taste, a thick white or yellowish coating on the back of the tongue, and dry mouth (xerostomia). Since we often become accustomed to our own smell, you might only notice it through the reactions of others or by testing your breath with a clean tissue.',
    whyItHappens:
      'Poor hygiene is the main culprit, but dry mouth is a major contributor because saliva is needed to wash away food particles and neutralize acids. Other causes include chronic sinus infections, gastric reflux, and systemic conditions like liver or kidney issues.',
    whyNotIgnore:
      'While often seen as a social issue, persistent halitosis is a major warning sign of active gum disease (periodontitis) or a hidden infection that could be damaging your oral health.',
    whenToSeeDentist:
      "If bad breath remains a problem even after you've improved your brushing, flossing, and tongue-scraping habits for at least two weeks.",
  },
  {
    id: 'oral_cancer',
    name: 'Oral Cancer',
    icon: '🔬',
    color: '#8B5CF6',
    bgColor: '#8B5CF61A',
    whatIsHappening:
      'Oral cancer is the result of abnormal cell growth in the mouth or throat. It can affect the lips, tongue, inner lining of the cheeks, gums, or the floor/roof of the mouth. In its early stages, it often looks like a harmless sore or discoloration, making it difficult for patients to identify without a professional screening.',
    whatPeopleNotice:
      'Look for a sore or ulcer that does not heal within 14 days. Other signs include persistent red or white patches (erythroplakia or leukoplakia), a lump or thickening of the tissue, difficulty chewing or swallowing, or a feeling like something is caught in the throat.',
    whyItHappens:
      'The highest risk factors are tobacco use (including smokeless tobacco/gutka) and excessive alcohol consumption. Other risks include prolonged sun exposure to the lips and infection with certain strains of the human papillomavirus (HPV).',
    whyNotIgnore:
      'Oral cancer is highly treatable if caught early, with survival rates over 80-90%. However, late-stage detection significantly complicates treatment and reduces the chances of a full recovery.',
    whenToSeeDentist:
      'Any sore, lump, or patch in the mouth that persists for more than two weeks REQUIRES an immediate professional examination.',
  },
  {
    id: 'sensitivity',
    name: 'Tooth Sensitivity',
    icon: '⚡',
    color: '#22C55E',
    bgColor: '#22C55E1A',
    whatIsHappening:
      "This occurs when the protective enamel on the tooth's crown or the cementum on the tooth's root wears away, exposing the 'dentin'. Dentin contains thousands of microscopic tubules that lead directly to the tooth's nerve center. When these tubules are exposed, external triggers travel straight to the nerve.",
    whatPeopleNotice:
      'A sudden, sharp flash of pain or deep ache when teeth are exposed to cold air, ice-cold water, hot coffee, or acidic/sweet foods. The pain usually subsides quickly once the trigger is removed but can be quite intense.',
    whyItHappens:
      'Aggressive brushing (scrubbing) with a hard-bristled brush, gum recession due to age or gum disease, teeth grinding (bruxism), and frequent consumption of highly acidic foods/drinks that erode enamel.',
    whyNotIgnore:
      "Sensitivity often masks other problems like cracked teeth or deep cavities. Over time, it can lead to a 'learned' avoidance of proper cleaning in painful areas, which ironically leads to more plaque buildup and further decay.",
    whenToSeeDentist:
      "If the sensitivity is severe, happens in a specific tooth, or if your regular 'sensitive' toothpaste hasn't improved the situation after a month of use.",
  },
  {
    id: 'mouth_ulcers',
    name: 'Mouth Ulcers (Canker Sores)',
    icon: '🔥',
    color: '#8B5CF6',
    bgColor: '#8B5CF61A',
    whatIsHappening:
      "These are small, shallow lesions that develop on the soft tissues in your mouth or at the base of your gums. Unlike cold sores, canker sores don't occur on the surface of your lips and aren't contagious, but they can be extremely painful and make eating or talking difficult.",
    whatPeopleNotice:
      'They usually appear as round or oval sores with a white or yellow center and a red, inflamed border. You might feel a tingling or burning sensation a day or two before the sore actually appears.',
    whyItHappens:
      'Triggers include minor injuries (like biting your cheek), stress, hormonal shifts, and allergic reactions to certain bacteria in toothpaste. They are also strongly linked to deficiencies in Vitamin B12, Zinc, Folic Acid, or Iron.',
    whyNotIgnore:
      'While self-healing, frequent outbreaks are a signal that your body is under stress or missing essential nutrients. In rare cases, complex ulcers can be associated with inflammatory bowel diseases or immune system disorders.',
    whenToSeeDentist:
      'Seek care for unusually large sores, sores that keep spreading, or those accompanied by a high fever or difficulty drinking fluids.',
  },
];
