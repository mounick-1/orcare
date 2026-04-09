package com.simats.orcare.data

import androidx.compose.ui.graphics.Color
import com.simats.orcare.ui.theme.*

data class Symptom(
    val title: String,
    val icon: String,
    val color: Color
)

data class SymptomDetail(
    val icon: String,
    // New Structure Fields
    val whatIsHappening: String,
    val whatPeopleNotice: String,
    val possibleReasons: List<String>,
    val whatToDo: List<String>,
    val whenToSeeDentist: String
)

object SymptomRepository {
    val symptomsList = listOf(
        Symptom("Tooth Pain", "🦷", IndigoPrimary),
        Symptom("Bleeding Gums", "💉", MintAccent),
        Symptom("Tooth Sensitivity", "❄️", IndigoPrimary.copy(alpha=0.7f)),
        Symptom("Oral Ulcers", "😷", MintAccent.copy(alpha=0.7f)),
        Symptom("Bad Breath", "🌬️", SlateMuted),
        Symptom("Swelling", "😫", MintAccent),
        Symptom("Chipped Tooth", "🔨", SlateDark),
        Symptom("Tooth Mobility", "🥴", IndigoPrimary.copy(alpha=0.6f)),
        Symptom("Receding Gums", "📉", MintAccent.copy(alpha=0.8f)),
        Symptom("Discolouration", "🟤", SlateDark.copy(alpha=0.8f)),
        Symptom("Yellow Deposit", "🟡", IndigoPrimary.copy(alpha=0.5f)),
        Symptom("Missing Tooth", "🚫", SlateLight),
        Symptom("Irregular Teeth", "〰️", IndigoPrimary),
        Symptom("Jaw Pain", "🤐", MintAccent)
    )

    val symptomDetailsMap = mapOf(
        "tooth pain" to SymptomDetail(
            icon = "🦷",
            whatIsHappening = "Pain in or around a tooth, indicating irritation, infection, or damage to the tooth nerve/pulp.",
            whatPeopleNotice = "Sharp or dull pain, often worse at night or when eating.",
            possibleReasons = listOf(
                "Tooth decay (cavity) or deep filling",
                "Cracked or fractured tooth",
                "Abscess (infection at the root)",
                "Gum disease or recession",
                "Grinding teeth at night"
            ),
            whatToDo = listOf(
                "Keep the area clean.",
                "Avoid chewing on that side.",
                "Rinse with water.",
                "Take over-the-counter pain relief if needed."
            ),
            whenToSeeDentist = "If pain lasts more than 2 days or is severe/continuous."
        ),
        "bleeding gums" to SymptomDetail(
            icon = "💉",
            whatIsHappening = "Inflammation of the gum tissue, causing it to bleed easily.",
            whatPeopleNotice = "Blood while brushing; red or swollen gums.",
            possibleReasons = listOf(
                "Gingivitis (early gum disease) from plaque buildup",
                "Brushing too hard or using a hard toothbrush",
                "Incorrect flossing technique",
                "Vitamin deficiencies",
                "Hormonal changes (e.g., pregnancy)"
            ),
            whatToDo = listOf(
                "Do not stop brushing.",
                "Continue to brush gently and correctly.",
                "Use a soft-bristled toothbrush.",
                "Floss daily to remove plaque between teeth."
            ),
            whenToSeeDentist = "If bleeding doesn't stop or is accompanied by loose teeth."
        ),
        "tooth sensitivity" to SymptomDetail(
            icon = "❄️",
            whatIsHappening = "Sharp, temporary pain when teeth are exposed to certain stimuli.",
            whatPeopleNotice = "Sudden sharp pain with cold, hot, or sweet triggers.",
            possibleReasons = listOf(
                "Worn tooth enamel from hard brushing",
                "Gum recession exposing sensitive roots",
                "Tooth decay or old fillings",
                "Cracked teeth",
                "Recent whitening treatment"
            ),
            whatToDo = listOf(
                "Use gentle brushing.",
                "Avoid extreme temperature foods.",
                "Switch to a toothpaste for sensitive teeth."
            ),
            whenToSeeDentist = "If sensitivity persists or becomes severe."
        ),
        "oral ulcers" to SymptomDetail(
            icon = "😷",
            whatIsHappening = "Small, painful lesions that develop in the mouth or at the base of the gums.",
            whatPeopleNotice = "Painful white or yellow sores; burning sensation.",
            possibleReasons = listOf(
                "Minor injury (biting cheek, sharp food)",
                "Stress or lack of sleep",
                "Vitamin deficiencies (B12, iron)",
                "Certain foods (spicy, acidic)",
                "Hormonal changes"
            ),
            whatToDo = listOf(
                "Keep the mouth clean.",
                "Avoid spicy foods.",
                "Rinse with warm salt water."
            ),
            whenToSeeDentist = "If ulcers last more than 2 weeks."
        ),
        "bad breath" to SymptomDetail(
            icon = "🌬️",
            whatIsHappening = "Unpleasant odor exhaled from the mouth.",
            whatPeopleNotice = "Bad taste in mouth; people moving away when you speak; white coating on tongue; dry mouth.",
            possibleReasons = listOf(
                "Poor oral hygiene (bacteria on teeth/tongue)",
                "Gum disease",
                "Dry mouth (xerostomia)",
                "Foods (garlic, onion)",
                "Tobacco usage"
            ),
            whatToDo = listOf(
                "Brush twice daily and floss once daily.",
                "Clean your tongue with a scraper or brush.",
                "Drink plenty of water to prevent dry mouth.",
                "Chew sugar-free gum to stimulate saliva."
            ),
            whenToSeeDentist = "If bad breath persists despite good hygiene (could be a sign of gum disease or other issues)."
        ),
        "swelling" to SymptomDetail(
            icon = "😫",
            whatIsHappening = "Enlargement or distention of mouth tissues due to inflammation or fluid.",
            whatPeopleNotice = "Puffy face or gums; a tight, painful feeling.",
            possibleReasons = listOf(
                "Tooth abscess (infection)",
                "Gum infection",
                "Blocked salivary gland",
                "Wisdom tooth eruption",
                "Trauma/Injury"
            ),
            whatToDo = listOf(
                "Do not press or massage the swelling.",
                "Keep the mouth clean.",
                "Rinse with warm salt water."
            ),
            whenToSeeDentist = "IMMEDIATELY. Any swelling accompanied by fever or difficulty swallowing."
        ),
        "chipped tooth" to SymptomDetail(
            icon = "🔨",
            whatIsHappening = "A piece of tooth enamel has broken off.",
            whatPeopleNotice = "Rough or sharp edge on a tooth; sensitivity to cold/hot; pain when biting.",
            possibleReasons = listOf(
                "Biting on hard objects (ice, candy, bones)",
                "Falls or sports accidents",
                "Teeth weakened by large old fillings",
                "Grinding teeth"
            ),
            whatToDo = listOf(
                "Rinse mouth with warm water.",
                "If there is bleeding, apply pressure with gauze.",
                "Cover any sharp edge with sugar-free gum (temporary).",
                "Eat soft foods."
            ),
            whenToSeeDentist = "As soon as possible to smooth the edge or repair the tooth before decay starts."
        ),
        "tooth mobility" to SymptomDetail(
            icon = "🥴",
            whatIsHappening = "A tooth feels loose or moves when touched.",
            whatPeopleNotice = "Sensation of tooth moving when eating/brushing; altered bite; gap widening between teeth.",
            possibleReasons = listOf(
                "Advanced gum disease (periodontitis) destroying bone support",
                "Trauma/Injury to the face/mouth",
                "Grinding/Clenching teeth heavily"
            ),
            whatToDo = listOf(
                "Do NOT wiggle the tooth with fingers or tongue.",
                "Stick to a soft diet.",
                "Keep the area clean by gentle brushing."
            ),
            whenToSeeDentist = "Promptly. Loose teeth can sometimes be saved with early treatment."
        ),
        "receding gums" to SymptomDetail(
            icon = "📉",
            whatIsHappening = "Gum tissue pulls back, exposing the tooth root.",
            whatPeopleNotice = "Teeth look longer than before; notch felt near the gum line; sensitivity to cold.",
            possibleReasons = listOf(
                "Brushing too hard / aggressive scrubbing",
                "Gum disease",
                "Smoking/Tobacco use",
                "Genetics (thin gums)",
                "Grinding teeth"
            ),
            whatToDo = listOf(
                "Switch to an extra-soft toothbrush.",
                "Use gentle circular brushing motion.",
                "Use a desensitizing toothpaste if sensitive."
            ),
            whenToSeeDentist = "For advice on preventing further recession and treating existing damage."
        ),
        "discolouration" to SymptomDetail(
            icon = "🟤",
            whatIsHappening = "Change in the natural color of teeth.",
            whatPeopleNotice = "Yellow, brown, black, or white spots/stains on teeth.",
            possibleReasons = listOf(
                "Foods/Drinks (coffee, tea, wine, berries)",
                "Tobacco use (smoking or chewing)",
                "Poor hygiene (plaque accumulation)",
                "Tooth decay (dark spots)",
                "Trauma to the tooth (turns grey/dark)"
            ),
            whatToDo = listOf(
                "Brush and floss regularly.",
                "Rinse with water after staining foods/drinks.",
                "Quit tobacco.",
                "Use a whitening toothpaste (cautiously)."
            ),
            whenToSeeDentist = "For professional cleaning or if a specific spot is dark (could be decay)."
        ),
        "yellow deposit" to SymptomDetail(
            icon = "🟡",
            whatIsHappening = "Accumulation of hard mineral deposits on teeth.",
            whatPeopleNotice = "Hard, rough yellow/brown material near the gum line or between teeth that doesn't brush off.",
            possibleReasons = listOf(
                "Plaque that was not removed hardening into tartar (calculus)",
                "High mineral content in saliva",
                "Poor brushing technique"
            ),
            whatToDo = listOf(
                "You cannot remove tartar at home; brushing won't work.",
                "Emphasize flossing to prevent future buildup."
            ),
            whenToSeeDentist = "For a professional scale and polish (cleaning). Only a dentist/hygienist can remove tartar."
        ),
        "missing tooth" to SymptomDetail(
            icon = "🚫",
            whatIsHappening = "Absence of a tooth in the dental arch.",
            whatPeopleNotice = "Gap in smile; difficulty chewing; food getting stuck in the gap.",
            possibleReasons = listOf(
                "Extraction due to decay or infection",
                "Trauma/Knocked out",
                "Congenitally missing (born without it)"
            ),
            whatToDo = listOf(
                "Keep the gap and surrounding teeth very clean.",
                "Watch for shifting of adjacent teeth."
            ),
            whenToSeeDentist = "To discuss replacement options (bridge, implant, denture) to prevent bite issues."
        ),
        "irregular teeth" to SymptomDetail(
            icon = "〰️",
            whatIsHappening = "Misalignment of teeth or jaws.",
            whatPeopleNotice = "Crooked, overlapping, or twisted teeth; gaps; difficulty cleaning; overbite/underbite.",
            possibleReasons = listOf(
                "Genetics (jaw size vs tooth size)",
                "Early loss of baby teeth",
                "Thumb sucking habit in childhood"
            ),
            whatToDo = listOf(
                "Be extra diligent with cleaning; crooked teeth trap more plaque.",
                "Use floss threaders or water flossers if needed."
            ),
            whenToSeeDentist = "For an orthodontic consultation (braces/aligners) if it affects function or aesthetics."
        ),
        "jaw pain" to SymptomDetail(
            icon = "🤐",
            whatIsHappening = "Discomfort in the jaw joint (TMJ) or muscles.",
            whatPeopleNotice = "Pain near ear/jaw; clicking/popping sound; difficulty opening mouth wide; headache.",
            possibleReasons = listOf(
                "Teeth grinding or clenching (bruxism)",
                "Stress/Anxiety",
                "Arthritis in the joint",
                "Misaligned bite"
            ),
            whatToDo = listOf(
                "Eat soft foods to rest the jaw.",
                "Apply warm moist heat to the jaw muscles.",
                "Avoid big yawns or gum chewing.",
                "Practice stress relief."
            ),
            whenToSeeDentist = "If pain persists, limits mouth opening, or you suspect you grind your teeth."
        )
    )
}
