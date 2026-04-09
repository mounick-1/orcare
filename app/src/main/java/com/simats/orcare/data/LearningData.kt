package com.simats.orcare.data

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.*
import androidx.compose.material.icons.automirrored.rounded.*
import com.simats.orcare.ui.theme.*

data class LearningCategory(
    val id: String,
    val title: String,
    val icon: ImageVector,
    val color: Color,
    val modules: List<LearningModule>
)

data class LearningModule(
    val id: String,
    val title: String,
    val duration: String,
    val lessonCount: Int,
    val objective: String,
    val icon: ImageVector,
    val lessons: List<Lesson>,
    val quiz: List<QuizQuestion>,
    val points: Int = 10
)

data class Lesson(
    val id: Int,
    val title: String,
    val content: String,
    val icon: ImageVector = Icons.Rounded.Check
)

data class QuizQuestion(
    val id: Int,
    val question: String,
    val options: List<String>,
    val correctAnswerIndex: Int
)

object LearningRepository {
    val categories = listOf(
        // 1. Daily Oral Hygiene
        LearningCategory(
            id = "daily_hygiene",
            title = "1. Daily Oral Hygiene",
            icon = Icons.Rounded.CleaningServices,
            color = Color(0xFF3B82F6), // Blue
            modules = listOf(
                LearningModule(
                    id = "hygiene_practices",
                    title = "Daily Practices",
                    duration = "5 min",
                    lessonCount = 6,
                    objective = "What everyone should do every day to prevent problems.",
                    icon = Icons.Rounded.Schedule,
                    lessons = listOf(
                        Lesson(1, "What is Daily Hygiene?", "Daily oral hygiene is the proactive habit of keeping your teeth, gums, tongue, and the entire oral cavity clean. It's not just about a bright smile; it's about removing the microbial biofilm (plaque) that constantly forms on your teeth. Effective daily care prevents 90% of dental problems before they even require a dentist's intervention.", Icons.Rounded.Info),
                        Lesson(2, "The Golden Rules", "The foundation of a healthy mouth consists of four pillars: Brushing effectively twice a day, cleaning between your teeth (interdental cleaning) once a day, scraping your tongue to remove volatile sulfur compounds, and maintaining constant hydration to support natural saliva flow.", Icons.Rounded.List),
                        Lesson(3, "The Silent Protector: Saliva", "Saliva is your mouth's most powerful natural defense. It contains essential minerals that 're-mineralize' early decay, neutralizes the acids produced by bacteria after eating, and physically washes away loose food particles. Drinking water throughout the day is the best way to keep your saliva production optimal.", Icons.Rounded.WaterDrop),
                        Lesson(4, "Night-Time: The Critical Hour", "Brushing before sleep is the most important session of the day. While you sleep, saliva production drops significantly. If plaque and food are left on your teeth overnight, bacteria have 7 to 8 hours of uninterrupted time to produce acid and damage your enamel without the neutralizing effect of saliva.", Icons.Rounded.Nightlight),
                        Lesson(5, "Gentle Precision over Force", "Many people mistakenly believe that 'harder is better'. In reality, aggressive scrubbing can lead to permanent gum recession and wear away your protective tooth enamel. Effectiveness comes from precise movements and ensuring the bristles reach every surface, not from the pressure applied.", Icons.Rounded.TouchApp),
                        Lesson(6, "The After-Meal Rinse", "If you cannot brush after a meal, at least rinse your mouth vigorously with plain water. This simple action helps dislodge trapped food debris and dilutes the sugars that bacteria use to create acid, significantly reducing the 'acid attack' time on your teeth.", Icons.Rounded.CleanHands)
                    ),
                    quiz = listOf(
                        QuizQuestion(1, "Why is saliva so important for your teeth?", listOf("It makes teeth white", "It neutralizes acids and remineralizes enamel", "It tastes good", "It hardens the gums"), 1),
                        QuizQuestion(2, "When is the most critical time to brush your teeth?", listOf("Immediately after waking up", "After breakfast", "Before going to sleep", "After lunch"), 2),
                        QuizQuestion(3, "What is a major risk of 'hard' brushing?", listOf("Teeth become too white", "Gum recession and enamel wear", "Toothbrush breaks", "Brush becomes too soft"), 1)
                    )
                ),
                LearningModule(
                    id = "brushing_technique",
                    title = "Mastering Brushing",
                    duration = "6 min",
                    lessonCount = 4,
                    objective = "Learn the Bass Technique: the gold standard for clinical plaque removal.",
                    icon = Icons.Rounded.Brush,
                    lessons = listOf(
                        Lesson(1, "The 45-Degree Angle", "Place your toothbrush bristles at the gum line at a 45-degree angle. This allows the bristles to slightly penetrate the 'sulcus'—the small gap between the tooth and the gum where the most dangerous bacteria hide and thrive.", Icons.Rounded.RotateRight),
                        Lesson(2, "Vibratory Circles", "Instead of a long back-and-forth scrubbing motion, use very small, circular vibratory movements. Each circle should cover about half a tooth. This jiggles the plaque loose without irritating the delicate gum tissue.", Icons.Rounded.Loop),
                        Lesson(3, "The Vertical Flick", "When cleaning the back side (inner surface) of your front teeth, hold the brush vertically. Use several short, up-and-down strokes using the front half of the brush. This area is a frequent hotspot for tartar buildup.", Icons.Rounded.Straighten),
                        Lesson(4, "The Two-Minute Rule", "Divide your mouth into four quadrants (upper-left, upper-right, lower-left, lower-right). Spend a full 30 seconds on each. Most people brush for less than 45 seconds; setting a timer ensures you don't miss any critical surfaces.", Icons.Rounded.Timer)
                    ),
                    quiz = listOf(
                        QuizQuestion(1, "What is the recommended angle for the brush against the gums?", listOf("Flat (0 degrees)", "Vertical (90 degrees)", "Angled (45 degrees)", "Opposite (180 degrees)"), 2),
                        QuizQuestion(2, "How should you clean the inner surface of front teeth?", listOf("Circular motions", "Hold brush vertically and flick", "Back and forth", "Don’t brush them"), 1),
                        QuizQuestion(3, "How long should you spend on each quadrant of your mouth?", listOf("10 seconds", "30 seconds", "1 minute", "2 minutes"), 1)
                    )
                ),
                LearningModule(
                    id = "flossing_guide",
                    title = "Deep Interdental Cleaning",
                    duration = "5 min",
                    lessonCount = 4,
                    objective = "Cleaning the 35% of tooth surfaces that a brush simply cannot reach.",
                    icon = Icons.Rounded.CleaningServices,
                    lessons = listOf(
                        Lesson(1, "The Invisible Threat", "A toothbrush primarily cleans the front, back, and top surfaces. This leaves the sides of your teeth—nearly 35% of the total surface area—untouched. This is where most cavities and adult gum disease begin.", Icons.Rounded.VisibilityOff),
                        Lesson(2, "The 'C-Shape' Mastery", "Don't just move the floss up and down like a saw. Once it's between the teeth, wrap the floss around the tooth in the shape of a 'C'. Gently slide it up and down against the side of the tooth, reaching slightly under the gumline.", Icons.Rounded.Architecture),
                        Lesson(3, "Avoid the 'Snap'", "Never snap the floss into your gums, as this can cause physical trauma and bleeding. Guide it gently using a zigzag motion. If flossing is difficult due to tight spaces, try using 'waxed' floss or a water flosser.", Icons.Rounded.Warning),
                        Lesson(4, "One Tooth, One Section", "As you move from one tooth to the next, use a fresh, clean section of the floss. This prevents you from simply relocating bacteria and food debris from one part of your mouth to another.", Icons.Rounded.CheckCircle)
                    ),
                    quiz = listOf(
                        QuizQuestion(1, "What percentage of tooth surface does a brush NOT reach?", listOf("10%", "25%", "35%", "50%"), 2),
                        QuizQuestion(2, "What is the correct way to move floss against a tooth?", listOf("Straight up and down", "Saw-like horizontal motion", "Wrapping it in a 'C' shape", "Only at the top"), 2),
                        QuizQuestion(3, "What should you do when moving to a new tooth?", listOf("Use the same floss section", "Use a fresh section of floss", "Stop flossing", "Rinse the floss in water"), 1)
                    )
                ),
                LearningModule(
                    id = "tongue_care",
                    title = "Tongue Care",
                    duration = "3 min",
                    lessonCount = 3,
                    objective = "Importance of tongue cleaning for fresh breath and oral health.",
                    icon = Icons.Rounded.RecordVoiceOver,
                    lessons = listOf(
                        Lesson(1, "Bacteria Trap", "The tongue's surface is full of tiny bumps that trap bacteria.", Icons.Rounded.BugReport),
                        Lesson(2, "Bad Breath", "Most bad breath (halitosis) originates from the back of the tongue.", Icons.Rounded.Air),
                        Lesson(3, "Cleaning Method", "Use a tongue scraper or your toothbrush from back to front.", Icons.Rounded.KeyboardDoubleArrowDown)
                    ),
                    quiz = listOf(
                        QuizQuestion(1, "Where does most bad breath originate?", listOf("Teeth", "Back of the tongue", "Palate", "Gums"), 1),
                        QuizQuestion(2, "Which tool is best for tongue cleaning?", listOf("Toothpick", "Tongue scraper/Brush", "Dental floss", "Rinsing only"), 1),
                        QuizQuestion(3, "How should you scrape your tongue?", listOf("Front to back", "Back to front", "Side to side", "Circular only"), 1)
                    )
                )
            )
        ),
        // 2. Prevention and Tools
        LearningCategory(
            id = "prevention_tools",
            title = "2. Prevention & Tools",
            icon = Icons.Rounded.Build,
            color = Color(0xFF10B981), // Emerald
            modules = listOf(
                LearningModule(
                    id = "preventive_habits",
                    title = "Preventive Habits",
                    duration = "4 min",
                    lessonCount = 4,
                    objective = "How to prevent dental problems.",
                    icon = Icons.Rounded.Shield,
                    lessons = listOf(
                        Lesson(1, "Regular Care", "Regular brushing and rinsing are the foundation of prevention.", Icons.Rounded.CleaningServices),
                        Lesson(2, "Healthy Eating", "Maintain healthy eating habits to support strong teeth.", Icons.Rounded.Restaurant),
                        Lesson(3, "Sugar Control", "Limit sugar frequency. It is more harmful than quantity.", Icons.Rounded.NoFood),
                        Lesson(4, "Tobacco", "Avoid tobacco in all forms to prevent gum disease and cancer.", Icons.Rounded.SmokeFree)
                    ),
                    quiz = listOf(
                        QuizQuestion(1, "What is more harmful regarding sugar?", listOf("Quantity", "Frequency", "Color", "Brand"), 1),
                        QuizQuestion(2, "What habit helps prevention?", listOf("Smoking", "Rinsing", "Snacking", "Sipping soda"), 1),
                        QuizQuestion(3, "Tobacco causes what?", listOf("Whiter teeth", "Gum disease", "Fresh breath", "Stronger enamel"), 1)
                    )
                ),
                LearningModule(
                    id = "common_tools",
                    title = "Your Dental Toolkit",
                    duration = "5 min",
                    lessonCount = 5,
                    objective = "Choosing and maintaining the right instruments for oral health.",
                    icon = Icons.Rounded.Brush,
                    lessons = listOf(
                        Lesson(1, "The Right Brush", "Always opt for 'Soft' or 'Extra-Soft' bristles. Hard bristles do not clean plaque better; they only increase the risk of gum damage. Replace your brush Every 3 months or immediately after being sick to avoid re-infection.", Icons.Rounded.Brush),
                        Lesson(2, "Paste with a Purpose", "The primary goal of toothpaste is to deliver fluoride to the teeth. Fluoride strengthens weakened enamel and can actually reverse early microscopic decay. Use a pea-sized amount; more isn't better, it just creates more foam.", Icons.Rounded.InvertColors),
                        Lesson(3, "Tongue Cleaners", "A dedicated tongue scraper is more efficient than a toothbrush at removing the biofilm on your tongue. It helps reduce bad breath by up to 75% and improves your sense of taste by clearing the taste buds.", Icons.Rounded.CleanHands),
                        Lesson(4, "Interdental Brushes", "For those with larger gaps between teeth, braces, or dental bridges, interdental brushes are often more effective than floss. They come in various sizes and should fit snugly without the wire touching the tooth.", Icons.Rounded.CleaningServices),
                        Lesson(5, "Tool Maintenance", "Rinse your toothbrush thoroughly after use and store it upright in an open area. Never cover a wet brush or store it in a closed container, as this encourages the growth of bacteria and mold.", Icons.Rounded.HealthAndSafety)
                    ),
                    quiz = listOf(
                        QuizQuestion(1, "Which bristle type is recommended by most dentists?", listOf("Hard", "Medium", "Soft", "Extra-Hard"), 2),
                        QuizQuestion(2, "What is the main role of fluoride in toothpaste?", listOf("Make the breath smell good", "Strengthen enamel and reverse microscopic decay", "Make the paste foam", "Whiten the teeth instantly"), 1),
                        QuizQuestion(3, "How should you store your toothbrush?", listOf("In a closed cap", "Lying down on the counter", "Upright in an open, dry area", "In a drawer"), 2)
                    )
                ),
                LearningModule(
                    id = "mouthwash_basics",
                    title = "Mouthwash Basics",
                    duration = "4 min",
                    lessonCount = 3,
                    objective = "How to choose and use mouthwash as an added layer of protection.",
                    icon = Icons.Rounded.Bloodtype,
                    lessons = listOf(
                        Lesson(1, "Not a Substitute", "Mouthwash adds protection but does not replace brushing or flossing.", Icons.Rounded.Warning),
                        Lesson(2, "Alcohol-Free", "Choose alcohol-free mouthwash to avoid drying out your mouth.", Icons.Rounded.Science),
                        Lesson(3, "Timing", "Don't rinse immediately after brushing to keep fluoride on your teeth.", Icons.Rounded.Timer)
                    ),
                    quiz = listOf(
                        QuizQuestion(1, "Does mouthwash replace brushing?", listOf("Yes", "No", "Sometimes", "Only at night"), 1),
                        QuizQuestion(2, "What type of mouthwash is generally better?", listOf("Alcohol-based", "Alcohol-free", "Highly acidic", "Sugar-based"), 1),
                        QuizQuestion(3, "When should you use mouthwash?", listOf("Immediately after brushing", "Before brushing", "At a different time than brushing", "Only once a week"), 2)
                    )
                ),
                LearningModule(
                    id = "interdental_tools",
                    title = "Interdental Tools",
                    duration = "5 min",
                    lessonCount = 3,
                    objective = "Beyond floss: using brushes and water flossers for gaps.",
                    icon = Icons.Rounded.Construction,
                    lessons = listOf(
                        Lesson(1, "Interdental Brushes", "Tiny brushes designed to clean larger gaps between teeth.", Icons.Rounded.CleaningServices),
                        Lesson(2, "Water Flossers", "Devices that use a stream of water to remove food and plaque.", Icons.Rounded.WaterDrop),
                        Lesson(3, "Correct Sizing", "The brush should fit snugly but comfortably in the gap.", Icons.Rounded.Straighten)
                    ),
                    quiz = listOf(
                        QuizQuestion(1, "Which tool is best for large gaps?", listOf("Normal toothbrush", "Interdental brush", "Toothpick", "Chewing gum"), 1),
                        QuizQuestion(2, "What do water flossers use?", listOf("Air", "Water stream", "Oil", "Laser"), 1),
                        QuizQuestion(3, "How should an interdental brush fit?", listOf("Very loose", "Snugly but comfortable", "Extremely tight", "Shouldn't fit"), 1)
                    )
                )
            )
        ),
        // 3. Common Dental Conditions
        LearningCategory(
            id = "common_conditions",
            title = "3. Common Dental Conditions",
            icon = Icons.Rounded.Warning,
            color = Color(0xFFF59E0B), // Amber
            modules = listOf(
                LearningModule(
                    id = "warning_signs",
                    title = "Your Mouth's Warning Signs",
                    duration = "5 min",
                    lessonCount = 8,
                    objective = "Learn to listen to the subtle signals your mouth sends before pain begins.",
                    icon = Icons.Rounded.Notifications,
                    lessons = listOf(
                        Lesson(1, "The Signal of Pain", "Pain is not the first sign of a problem; it's often the last. By the time a tooth honestly hurts, the damage has usually reached the sensitive inner nerve. Early monitoring is key.", Icons.Rounded.MoodBad),
                        Lesson(2, "Bleeding is Not Normal", "If your hands bled when you washed them, you'd be alarmed. The same applies to gums. Bleeding during brushing is a clear cry for help from inflamed tissues.", Icons.Rounded.Bloodtype),
                        Lesson(3, "Temperature Sensitivity", "Sharp pain from hot or cold can indicate receding gums, a cracked tooth, or a thinning of the protective enamel layer.", Icons.Rounded.AcUnit),
                        Lesson(4, "Persistent Odor", "Bad breath that doesn't go away after cleaning often points to bacteria hiding in places your brush can't reach, such as deep gum pockets.", Icons.Rounded.Air),
                        Lesson(5, "Localized Swelling", "Any lump or swelling in the gums or cheeks is a sign of an active immune response to an infection, such as a dental abscess.", Icons.Rounded.Face),
                        Lesson(6, "The Two-Week Ulcer Rule", "Most mouth sores heal within 10 days. Any ulcer or patch that persists for more than 14 days needs a professional check for more serious conditions.", Icons.Rounded.Healing),
                        Lesson(7, "Color Changes", "Watch for white or dark spots on the teeth. These aren't just stains; they are often the visible signs of early demineralization or active decay.", Icons.Rounded.ColorLens),
                        Lesson(8, "Loose Teeth", "Unless you're a child losing milk teeth, a loose tooth is an emergency. It indicates a severe loss of the bone structure that supports your smile.", Icons.Rounded.FormatAlignJustify)
                    ),
                    quiz = listOf(
                        QuizQuestion(1, "Is pain the first sign of a dental problem?", listOf("Yes, always", "Usually not; it's often the last sign", "Only for kids", "Only if it bleeds"), 1),
                        QuizQuestion(2, "What should you do if an ulcer lasts more than two weeks?", listOf("Wait another week", "See a dentist immediately", "Apply more toothpaste", "Ignore it"), 1),
                        QuizQuestion(3, "What does a loose tooth in an adult signify?", listOf("A new tooth is coming", "Severe loss of supporting bone", "Healthy mouth", "Normal aging"), 1)
                    )
                ),
                LearningModule(
                    id = "understanding_conditions",
                    title = "Understanding Progression",
                    duration = "3 min",
                    lessonCount = 3,
                    objective = "Why 'waiting until it hurts' is the most expensive dental mistake.",
                    icon = Icons.Rounded.Lightbulb,
                    lessons = listOf(
                        Lesson(1, "The Silent Phase", "90% of dental issues—including cavities and gum disease—are completely painless in their early, most treatable stages. Regular checkups 'see' what you cannot feel.", Icons.Rounded.VolumeOff),
                        Lesson(2, "Exponential Decay", "Once a cavity breaks through the hard enamel into the softer dentin, it spreads twice as fast. Waiting just a few months can turn a simple filling into a root canal.", Icons.AutoMirrored.Rounded.TrendingUp),
                        Lesson(3, "Prevention is Wealth", "Fixing a problem early is 10 times cheaper and 100 times less painful than waiting for an emergency. Your daily habits are your best financial investment.", Icons.Rounded.Shield)
                    ),
                    quiz = listOf(
                        QuizQuestion(1, "Are most early dental problems painful?", listOf("Yes, very", "No, they are typically silent", "Only at night", "Only when eating sugar"), 1),
                        QuizQuestion(2, "How does decay speed change in the dentin layer?", listOf("It stops", "It slows down", "It spreads much faster", "It disappears"), 2),
                        QuizQuestion(3, "What is the best way to save money on dental care?", listOf("Waiting for pain", "Early prevention and checkups", "Buying expensive brushes", "Avoiding the dentist"), 1)
                    )
                ),
                LearningModule(
                    id = "decay_stages",
                    title = "The 3 Stages of Decay",
                    duration = "5 min",
                    lessonCount = 3,
                    objective = "Visualizing the journey from a tiny spot to a deep infection.",
                    icon = Icons.Rounded.Analytics,
                    lessons = listOf(
                        Lesson(1, "Stage 1: Enamel Demineralization", "Decay starts as a chalky white spot on the tooth surface. At this stage, the enamel is just losing minerals. With fluoride and better cleaning, this can actually be reversed!", Icons.Rounded.BrightnessLow),
                        Lesson(2, "Stage 2: Dentin Invasion", "The acid has broken through the enamel. The dentin is softer and contains nerve-connected tubules. You will now start to feel sensitivity to sweets and cold.", Icons.Rounded.Warning),
                        Lesson(3, "Stage 3: Pulp Infection", "The bacteria reach the 'pulp'—the living heart of the tooth. This causes severe, throbbing pain, pus formation (abscess), and requires a root canal to save the tooth.", Icons.Rounded.FlashOn)
                    ),
                    quiz = listOf(
                        QuizQuestion(1, "When is a cavity still reversible?", listOf("Stage 1: Enamel demineralization", "Stage 2: Dentin invasion", "Stage 3: Pulp infection", "Never"), 0),
                        QuizQuestion(2, "What is found in the pulp of the tooth?", listOf("More enamel", "Nerves and blood vessels", "Solid bone", "Air"), 1),
                        QuizQuestion(3, "What happens when decay reaches the pulp?", listOf("Nothing", "Sensitivity only", "Severe pain and infection", "Tooth turns blue"), 2)
                    )
                ),
                LearningModule(
                    id = "gum_disease_stages",
                    title = "Gum Disease Stages",
                    duration = "5 min",
                    lessonCount = 3,
                    objective = "From healthy gums to tooth loss.",
                    icon = Icons.Rounded.WaterfallChart,
                    lessons = listOf(
                        Lesson(1, "Gingivitis", "Red, swollen gums that bleed. Reversible with good hygiene.", Icons.Rounded.Bloodtype),
                        Lesson(2, "Periodontitis", "Gums pull away from teeth, forming pockets that trap plaque.", Icons.Rounded.DensitySmall),
                        Lesson(3, "Advanced Loss", "The bone and tissue supporting teeth are destroyed.", Icons.Rounded.RemoveCircle)
                    ),
                    quiz = listOf(
                        QuizQuestion(1, "Which stage of gum disease is reversible?", listOf("Gingivitis", "Periodontitis", "Advanced stage", "None"), 0),
                        QuizQuestion(2, "What forms in periodontitis?", listOf("New bone", "Pockets", "Stronger roots", "More enamel"), 1),
                        QuizQuestion(3, "What is the ultimate result of untreated gum disease?", listOf("Whiter teeth", "Tooth loss", "Faster growth", "Better breath"), 1)
                    )
                )
            )
        ),
        // 4. Specialized Care
        LearningCategory(
            id = "specialized_care",
            title = "4. Specialized Care",
            icon = Icons.Rounded.Groups, // Changed from Diversity1
            color = Color(0xFF8B5CF6), // Violet
            modules = listOf(
                LearningModule(
                    id = "sc_children",
                    title = "A. Children",
                    duration = "4 min",
                    lessonCount = 3,
                    objective = "Care for milk teeth and early habits.",
                    icon = Icons.Rounded.ChildCare,
                    lessons = listOf(
                        Lesson(1, "Milk Teeth", "Milk teeth also need care. They hold space for permanent teeth.", Icons.Rounded.ChildFriendly),
                        Lesson(2, "Early Habits", "Early habits affect permanent teeth health.", Icons.Rounded.ThumbUp),
                        Lesson(3, "Night Milk", "Night-time milk without cleaning causes rapid decay (Bottle Rot).", Icons.Rounded.NoDrinks)
                    ),
                    quiz = listOf(
                        QuizQuestion(1, "Do milk teeth need care?", listOf("No", "Yes", "Only front ones", "Sometimes"), 1),
                        QuizQuestion(2, "What causes decay at night for kids?", listOf("Water", "Milk without cleaning", "Air", "Sleeping"), 1),
                        QuizQuestion(3, "Why are milk teeth important?", listOf("They are white", "Affect permanent teeth", "Temporary only", "No reason"), 1)
                    )
                ),
                LearningModule(
                    id = "sc_elderly",
                    title = "B. Elderly",
                    duration = "4 min",
                    lessonCount = 4,
                    objective = "Specific issues for aging adults.",
                    icon = Icons.Rounded.Elderly,
                    lessons = listOf(
                        Lesson(1, "Gum Recession", "Roots become exposed as gums recede.", Icons.AutoMirrored.Rounded.TrendingDown),
                        Lesson(2, "Tooth Wear", "Enamel wears down over a lifetime of use.", Icons.Rounded.Architecture),
                        Lesson(3, "Dry Mouth", "Often caused by medications, leading to cavities.", Icons.Rounded.Water),
                        Lesson(4, "Denture Care", "Clean daily and remove at night to let gums rest.", Icons.Rounded.CleaningServices)
                    ),
                    quiz = listOf(
                        QuizQuestion(1, "What is a common issue for elderly?", listOf("New teeth", "Dry mouth", "Growing pains", "Braces"), 1),
                        QuizQuestion(2, "How often should dentures be cleaned?", listOf("Weekly", "Daily", "Monthly", "Never"), 1),
                        QuizQuestion(3, "Why remove dentures at night?", listOf("Comfort", "Prevent irritation/infection", "Save wear", "Dry them"), 1)
                    )
                ),
                LearningModule(
                    id = "sc_ortho",
                    title = "C. Orthodontic Patients",
                    duration = "4 min",
                    lessonCount = 3,
                    objective = "Hygiene with braces.",
                    icon = Icons.Rounded.Grid3x3,
                    lessons = listOf(
                        Lesson(1, "Food Trapping", "Food gets stuck easily in wires and brackets.", Icons.Rounded.Restaurant),
                        Lesson(2, "Difficulty", "Cleaning is more difficult but essential.", Icons.Rounded.CleaningServices),
                        Lesson(3, "Decay Risk", "Higher risk of decay if hygiene is poor.", Icons.Rounded.Warning)
                    ),
                    quiz = listOf(
                        QuizQuestion(1, "What is a risk with braces?", listOf("Decay", "Whiter teeth", "Stronger gums", "Less food"), 0),
                        QuizQuestion(2, "Is cleaning easier with braces?", listOf("Yes", "No", "Same", "Doesn't matter"), 1),
                        QuizQuestion(3, "What gets stuck easily?", listOf("Water", "Food", "Air", "Toothpaste"), 1)
                    )
                ),
                LearningModule(
                    id = "sc_pregnant",
                    title = "D. Pregnant Women",
                    duration = "3 min",
                    lessonCount = 3,
                    objective = "Oral care during pregnancy.",
                    icon = Icons.Rounded.PregnantWoman,
                    lessons = listOf(
                        Lesson(1, "Bleeding Gums", "Gums may bleed easily due to hormonal changes.", Icons.Rounded.Bloodtype),
                        Lesson(2, "Importance", "Oral hygiene is very important for mother and baby.", Icons.Rounded.Favorite),
                        Lesson(3, "Safety", "Dental care is safe with guidance.", Icons.Rounded.HealthAndSafety)
                    ),
                    quiz = listOf(
                        QuizQuestion(1, "What typically happens to gums during pregnancy?", listOf("Turn blue", "Bleed easily", "Shrink", "Harden"), 1),
                        QuizQuestion(2, "Is dental care safe during pregnancy?", listOf("No", "Yes, with guidance", "Never", "Only X-rays"), 1),
                        QuizQuestion(3, "Is hygiene important during pregnancy?", listOf("No", "Somewhat", "Very important", "Not really"), 2)
                    )
                )
            )
        ),
        // 5. All Common Dental Procedures
        LearningCategory(
            id = "dental_procedures",
            title = "5. All Common Dental Procedures",
            icon = Icons.Rounded.MedicalServices,
            color = Color(0xFF6366F1), // Indigo
            modules = listOf(
                LearningModule(
                    id = "proc_checkup",
                    title = "Dental Check-up",
                    duration = "3 min",
                    lessonCount = 3,
                    objective = "Routine examination details.",
                    icon = Icons.Rounded.Event,
                    lessons = listOf(
                        Lesson(1, "What it is", "A routine examination of teeth, gums, and mouth.", Icons.Rounded.Search),
                        Lesson(2, "Why needed", "Detect problems early. Prevent pain and complications.", Icons.Rounded.Shield),
                        Lesson(3, "How often", "Every 6 months. Earlier if symptoms exist.", Icons.Rounded.Schedule)
                    ),
                    quiz = listOf(
                        QuizQuestion(1, "How often should you have a check-up?", listOf("Every month", "Every 6 months", "Every year", "When it hurts"), 1),
                        QuizQuestion(2, "Why is it needed?", listOf("Detect early", "Spend money", "Chat", "Sleep"), 0),
                        QuizQuestion(3, "What is examined?", listOf("Teeth only", "Teeth, gums, mouth", "Tongue only", "Lips only"), 1)
                    )
                ),
                LearningModule(
                    id = "proc_scaling",
                    title = "Scaling (Cleaning)",
                    duration = "3 min",
                    lessonCount = 3,
                    objective = "Professional cleaning facts.",
                    icon = Icons.Rounded.CleaningServices,
                    lessons = listOf(
                        Lesson(1, "What it is", "Removal of hard and soft deposits from teeth.", Icons.Rounded.CleaningServices),
                        Lesson(2, "Why needed", "Prevents gum disease. Stops bleeding and bad breath.", Icons.Rounded.Healing),
                        Lesson(3, "Myth", "Scaling does NOT loosen teeth. It removes infection that makes teeth loose.", Icons.Rounded.FactCheck)
                    ),
                    quiz = listOf(
                        QuizQuestion(1, "Does scaling loosen teeth?", listOf("Yes", "No", "Sometimes", "Maybe"), 1),
                        QuizQuestion(2, "What does scaling remove?", listOf("Enamel", "Deposits", "Gums", " Roots"), 1),
                        QuizQuestion(3, "Why is it needed?", listOf("Prevent gum disease", "Whiten only", "Cause pain", "Loosen teeth"), 0)
                    )
                ),
                LearningModule(
                    id = "proc_fillings",
                    title = "Dental Fillings",
                    duration = "3 min",
                    lessonCount = 3,
                    objective = "Repairing decay.",
                    icon = Icons.Rounded.Build,
                    lessons = listOf(
                        Lesson(1, "What it is", "Repairing a tooth damaged by decay.", Icons.Rounded.Build),
                        Lesson(2, "Why needed", "Stops cavity from growing. Prevents pain and infection.", Icons.Rounded.Stop),
                        Lesson(3, "Understanding", "Early fillings are simple. Delay increases treatment complexity.", Icons.Rounded.Timer)
                    ),
                    quiz = listOf(
                        QuizQuestion(1, "What do fillings repair?", listOf("Gums", "Decayed tooth", "Tongue", "Bone"), 1),
                        QuizQuestion(2, "Why are they needed?", listOf("Stop cavity growth", "Look good", "Feel cold", "Remove tooth"), 0),
                        QuizQuestion(3, "What does delay cause?", listOf("Simplifies treatment", "Increases complexity", "Nothing", "Healing"), 1)
                    )
                ),
                LearningModule(
                    id = "proc_rct",
                    title = "Root Canal Treatment",
                    duration = "3 min",
                    lessonCount = 3,
                    objective = "Saving the natural tooth.",
                    icon = Icons.Rounded.Healing,
                    lessons = listOf(
                        Lesson(1, "What it is", "Cleaning infection from inside the tooth and saving it.", Icons.Rounded.CleaningServices),
                        Lesson(2, "Why needed", "Severe pain. Deep infection.", Icons.Rounded.Healing),
                        Lesson(3, "Reassurance", "Done to relieve pain, not cause it. Saves natural tooth.", Icons.Rounded.Favorite)
                    ),
                    quiz = listOf(
                        QuizQuestion(1, "What is the goal of RCT?", listOf("Remove tooth", "Save tooth", "Cause pain", "Clean gums"), 1),
                        QuizQuestion(2, "When is it needed?", listOf("Small cavity", "Deep infection", "Stain", "Chipped edge"), 1),
                        QuizQuestion(3, "Does RCT cause pain?", listOf("Yes", "No, it relieves it", "Always", "Severe pain"), 1)
                    )
                ),
                LearningModule(
                    id = "proc_extraction",
                    title = "Tooth Extraction",
                    duration = "3 min",
                    lessonCount = 3,
                    objective = "Removal of damaged teeth.",
                    icon = Icons.Rounded.Delete,
                    lessons = listOf(
                        Lesson(1, "What it is", "Removal of a severely damaged tooth.", Icons.Rounded.Delete),
                        Lesson(2, "Why needed", "Tooth cannot be saved. Severe infection.", Icons.Rounded.Warning),
                        Lesson(3, "After removal", "Area needs care. Replacement may be advised later.", Icons.Rounded.Healing)
                    ),
                    quiz = listOf(
                        QuizQuestion(1, "When is extraction needed?", listOf("Small cavity", "Tooth cannot be saved", "Stained tooth", "Sensitive tooth"), 1),
                        QuizQuestion(2, "What happens after?", listOf("Nothing", "Area needs care", "Tooth grows back", "Pain stops forever"), 1),
                        QuizQuestion(3, "Is replacement advised?", listOf("Never", "May be advised", "Always", "No"), 1)
                    )
                ),
                LearningModule(
                    id = "proc_wisdom",
                    title = "Wisdom Tooth Problems",
                    duration = "3 min",
                    lessonCount = 3,
                    objective = "Managing the last teeth.",
                    icon = Icons.Rounded.Psychology,
                    lessons = listOf(
                        Lesson(1, "What it is", "Last teeth to come in, often without enough space.", Icons.Rounded.DateRange),
                        Lesson(2, "Problems", "Pain, swelling, food trapping.", Icons.Rounded.Warning),
                        Lesson(3, "Note", "Not all wisdom teeth need removal. Dentist decides based on symptoms.", Icons.Rounded.Assignment)
                    ),
                    quiz = listOf(
                        QuizQuestion(1, "Do all wisdom teeth need removal?", listOf("Yes", "No", "Always", "Never"), 1),
                        QuizQuestion(2, "Common problem with wisdom teeth?", listOf("Space", "Food trapping", "Whiteness", "Shape"), 1),
                        QuizQuestion(3, "Who decides on removal?", listOf("You", "Dentist", "Friend", "Google"), 1)
                    )
                )
            )
        ),
        // 6. Systemic Health & Oral Health
        LearningCategory(
            id = "systemic_health",
            title = "6. Systemic Health & Oral Health",
            icon = Icons.Rounded.MonitorHeart,
            color = Color(0xFFEC4899), // Pink
            modules = listOf(
                LearningModule(
                    id = "mouth_body_connection",
                    title = "Mouth-Body Connection",
                    duration = "4 min",
                    lessonCount = 4,
                    objective = "The mouth is part of the body, not separate from it.",
                    icon = Icons.Rounded.Sync,
                    lessons = listOf(
                        Lesson(1, "Oral to Body", "Gum disease can affect: Heart health, Diabetes control, Pregnancy outcomes.", Icons.AutoMirrored.Rounded.ArrowForward),
                        Lesson(2, "Body to Oral", "Diabetes -> gum disease, infections. Stress -> grinding, jaw pain. Poor nutrition -> ulcers, gum problems.", Icons.AutoMirrored.Rounded.ArrowBack),
                        Lesson(3, "Message", "The mouth is part of the body, not separate from it.", Icons.Rounded.Message),
                        Lesson(4, "Care", "Taking care of your mouth is taking care of your body.", Icons.Rounded.Favorite)
                    ),
                    quiz = listOf(
                        QuizQuestion(1, "What condition can gum disease affect?", listOf("Hair growth", "Heart health", "Eye color", "Height"), 1),
                        QuizQuestion(2, "How does stress affect the mouth?", listOf("Cavities", "Grinding/Jaw pain", "Whiter teeth", "Bad breath"), 1),
                        QuizQuestion(3, "Is the mouth separate from the body?", listOf("Yes", "No", "Sometimes", "Maybe"), 1)
                    )
                ),
                LearningModule(
                    id = "diabetes_oral_health",
                    title = "Diabetes & Oral Health",
                    duration = "5 min",
                    lessonCount = 3,
                    objective = "Understanding the two-way relationship between diabetes and gum disease.",
                    icon = Icons.Rounded.MonitorWeight,
                    lessons = listOf(
                        Lesson(1, "Higher Risk", "People with diabetes are at higher risk for gum disease and dry mouth.", Icons.Rounded.Warning),
                        Lesson(2, "Blood Sugar", "Gum disease can make it harder to control blood sugar levels.", Icons.Rounded.Timeline),
                        Lesson(3, "Slow Healing", "Diabetes can slow down the healing of mouth infections.", Icons.Rounded.HourglassBottom)
                    ),
                    quiz = listOf(
                        QuizQuestion(1, "What is a common oral risk for people with diabetes?", listOf("Stronger enamel", "Gum disease", "Zero cavities", "Whiter teeth"), 1),
                        QuizQuestion(2, "Can gum disease affect blood sugar?", listOf("Yes", "No", "Only for kids", "Never"), 0),
                        QuizQuestion(3, "How does diabetes affect healing in the mouth?", listOf("Speeds it up", "Doesn't affect it", "Slows it down", "Stops it completely"), 2)
                    )
                ),
                LearningModule(
                    id = "heart_oral_link",
                    title = "Heart Health Link",
                    duration = "4 min",
                    lessonCount = 3,
                    objective = "How bacteria in the mouth can affect your heart.",
                    icon = Icons.Rounded.Favorite,
                    lessons = listOf(
                        Lesson(1, "Bacteria Entry", "Gum bacteria can enter the bloodstream through inflamed gums.", Icons.Rounded.ArrowCircleRight),
                        Lesson(2, "Inflammation", "This bacteria can cause inflammation in the heart's vessels.", Icons.Rounded.Healing),
                        Lesson(3, "Prevention", "Preventing gum disease helps reduce the risk of heart-related issues.", Icons.Rounded.Shield)
                    ),
                    quiz = listOf(
                        QuizQuestion(1, "How can mouth bacteria reach the heart?", listOf("Through food", "Through the bloodstream", "Through breathing", "Through talking"), 1),
                        QuizQuestion(2, "What can oral bacteria cause in heart vessels?", listOf("Growth", "Cleaning", "Inflammation", "Color change"), 2),
                        QuizQuestion(3, "Does oral hygiene help heart health?", listOf("No", "Yes", "Only for athletes", "Only at night"), 1)
                    )
                ),
                LearningModule(
                    id = "nutrition_oral_health",
                    title = "Nutrition for Health",
                    duration = "5 min",
                    lessonCount = 4,
                    objective = "Fueling your body for a healthy smile.",
                    icon = Icons.Rounded.Restaurant,
                    lessons = listOf(
                        Lesson(1, "Calcium & Vitamin D", "Essential for strong teeth and bones. Found in dairy and leafy greens.", Icons.Rounded.Egg),
                        Lesson(2, "Vitamin C", "Vital for healthy gums. Prevents bleeding and inflammation.", Icons.Rounded.Favorite),
                        Lesson(3, "Crunchy Veggies", "Fruits and veggies like apples and carrots scan clean teeth as you eat.", Icons.Rounded.Star),
                        Lesson(4, "Water", "The best drink for your teeth. Washes away food and provides fluoride.", Icons.Rounded.WaterDrop)
                    ),
                    quiz = listOf(
                        QuizQuestion(1, "Which vitamin is most vital for healthy gums?", listOf("Vitamin A", "Vitamin B", "Vitamin C", "Vitamin K"), 2),
                        QuizQuestion(2, "What is the best drink for your teeth?", listOf("Soda", "Fruit Juice", "Sports Drink", "Water"), 3),
                        QuizQuestion(3, "Which minerals keep teeth strong?", listOf("Iron", "Calcium & Phosphorus", "Zinc", "Copper"), 1)
                    )
                )
            )
        )
    )
    
       fun getModule(id: String): LearningModule? {
        return categories.flatMap { it.modules }.find { it.id == id }
    }
}
