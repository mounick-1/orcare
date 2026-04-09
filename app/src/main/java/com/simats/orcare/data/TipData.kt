package com.simats.orcare.data

import java.util.Calendar

data class Tip(
    val id: Int,
    val title: String,
    val description: String,
    val category: String, // Hygiene, Food, Myth Busting
    val icon: String, // Emoji
    val isSaved: Boolean = false
)

object TipRepository {
    val categories = listOf("All", "Hygiene", "Food", "Lifestyle", "Myth Busting", "Age 7-9")
    
    // NEW: Daily Brushing Tips List
    val dailyBrushingTips = listOf(
        Tip(1, "Brush at Night", "Brush at night before sleeping — it is the most important brushing of the day.", "Hygiene", "🌙"),
        Tip(2, "2 Minutes Rule", "Brush for at least 2 minutes to ensure all surfaces are clean.", "Hygiene", "⏱️"),
        Tip(3, "Soft Bristles", "Always use a soft-bristled toothbrush to protect your gums and enamel.", "Hygiene", "🪥"),
        Tip(4, "Tongue Cleaning", "Clean your tongue daily to remove bacteria and freshen breath.", "Hygiene", "👅"),
        Tip(5, "Replace Brush", "Replace your toothbrush every 3 months or after recovering from illness.", "Hygiene", "🔄"),
        Tip(6, "Fluoride Power", "Use fluoride toothpaste to strengthen enamel and prevent cavities.", "Hygiene", "🦷"),
        Tip(7, "Spit, Don't Rinse", "Spit out excess toothpaste but don't rinse with water immediately.", "Hygiene", "🚫"),
        Tip(8, "Floss Daily", "Flossing removes plaque from between teeth where your brush can't reach.", "Hygiene", "🧵"),
        Tip(9, "Gentle Motion", "Use gentle circular motions; scrubbing hard can damage gums.", "Hygiene", "💫"),
        Tip(10, "Wait After Eating", "Wait 30 minutes after eating acidic foods before brushing.", "Hygiene", "⏳"),
        Tip(11, "Hydration", "Drink plenty of water to wash away food particles and keep saliva flowing.", "Food", "💧"),
        Tip(12, "Sugar Control", "Limit sugary snacks and drinks to mealtimes to reduce acid attacks.", "Food", "🍬"),
        Tip(13, "Tobacco Warning", "Tobacco use significantly increases the risk of gum disease and oral cancer.", "Lifestyle", "🚭"),
        Tip(14, "Mouthwash", "Use an antimicrobial mouthwash to reduce plaque and gingivitis.", "Hygiene", "🧴"),
        Tip(15, "Check Your Gums", "Healthy gums are pink and don't bleed. See a dentist if they do.", "Hygiene", "🔍"),
        Tip(16, "Visit Dentist", "Regular dental check-ups every 6 months are key to prevention.", "Hygiene", "👨‍⚕️"),
        Tip(17, "Clean Between", "Use interdental brushes for larger gaps between teeth.", "Hygiene", "🔧"),
        Tip(18, "Limit Soda", "Carbonated drinks, even sugar-free ones, can erode tooth enamel.", "Food", "🥤"),
        Tip(19, "Healthy Snacks", "Choose cheese, yogurt, or crunchy veggies instead of chips.", "Food", "🥕"),
        Tip(20, "Protect Teeth", "Wear a mouthguard during contact sports to prevent injuries.", "Lifestyle", "🛡️"),
        Tip(21, "Kids Brushing", "Supervise children's brushing until they are about 7 or 8 years old.", "Age 7-9", "👶"),
        Tip(22, "Dry Mouth?", "Chew sugar-free gum to stimulate saliva flow.", "Lifestyle", "🌵"),
        Tip(23, "Stress & Teeth", "Stress can lead to teeth grinding. Talk to your dentist about a nightguard.", "Lifestyle", "😬"),
        Tip(24, "Vitamin C", "Eat Vitamin C-rich foods like oranges for healthy gums.", "Food", "🍊"),
        Tip(25, "Calcium", "Dairy products provide calcium needed to keep teeth strong.", "Food", "🥛"),
        Tip(26, "Straw Trick", "Use a straw for sugary drinks to bypass teeth surfaces.", "Lifestyle", "🥤"),
        Tip(27, "Whiten Safely", "Ask your dentist before trying home whitening remedies.", "Myth Busting", "✨"),
        Tip(28, "Bleeding Myth", "Bleeding gums need MORE gentle brushing, not less.", "Myth Busting", "🩸"),
        Tip(29, "Hard Brushing", "Brushing harder does NOT clean better; it causes damage.", "Myth Busting", "🛑"),
        Tip(30, "Baby Teeth", "Baby teeth are important for spacing adult teeth. Keep them clean!", "Age 7-9", "🦷")
    )

    // Existing list kept for "All Tips" screen, merged with new ones if needed or kept separate.
    // For now, mapping dailyBrushingTips to the main list as well for consistency.
    val tipsList = dailyBrushingTips + listOf(
         Tip(31, "Whitening myths", "Lemon juice does not whiten teeth safely. It can damage enamel.", "Myth Busting", "🍋"),
         Tip(32, "Knocked-out tooth", "If a tooth is knocked out, keep it moist in milk and see a dentist within 30 mins.", "Hygiene", "🥛"),
         Tip(33, "Aspirin on gums", "Never place aspirin directly on gums; it causes chemical burns.", "Myth Busting", "💊"),
         Tip(34, "Charcoal Warning", "Charcoal toothpaste can be abrasive and wear down enamel.", "Myth Busting", "⚫")
    )
    
    fun getDailyTip(): Tip {
        val calendar = Calendar.getInstance()
        val dayOfYear = calendar.get(Calendar.DAY_OF_YEAR)
        // Modulo operator ensures we rotate through the list endlessly
        val index = (dayOfYear - 1) % dailyBrushingTips.size
        return dailyBrushingTips[index]
    }
}
