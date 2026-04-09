import os
import re

directory = r"c:\Users\nanda\AndroidStudioProjects\ORCare\app\src\main\java\com\simats\orcare\ui"

imports_to_ensure = [
    "import androidx.compose.foundation.BorderStroke",
    "import androidx.compose.foundation.background",
    "import androidx.compose.foundation.clickable",
    "import androidx.compose.foundation.layout.*",
    "import androidx.compose.foundation.shape.CircleShape",
    "import androidx.compose.foundation.shape.RoundedCornerShape",
    "import androidx.compose.material3.*",
    "import androidx.compose.runtime.*",
    "import androidx.compose.ui.Alignment",
    "import androidx.compose.ui.Modifier",
    "import androidx.compose.ui.draw.clip",
    "import androidx.compose.ui.draw.shadow",
    "import androidx.compose.ui.graphics.Brush",
    "import androidx.compose.ui.graphics.Color",
    "import androidx.compose.ui.res.painterResource",
    "import androidx.compose.ui.text.font.FontWeight",
    "import androidx.compose.ui.text.style.TextAlign",
    "import androidx.compose.ui.unit.dp",
    "import androidx.compose.ui.unit.sp",
    "import kotlinx.coroutines.delay",
    "import com.simats.orcare.ui.components.*"
]

for root, _, files in os.walk(directory):
    for file in files:
        if file.endswith(".kt"):
            filepath = os.path.join(root, file)
            with open(filepath, 'r', encoding='utf-8') as f:
                content = f.read()

            new_imports = []
            
            for imp in imports_to_ensure:
                if imp not in content:
                    # simplistic check for some
                    if "delay" in imp and "delay(" not in content:
                        continue
                    if "TextAlign" in imp and "TextAlign." not in content:
                        continue
                    if "CircleShape" in imp and "CircleShape" not in content:
                        continue
                    if "RoundedCornerShape" in imp and "RoundedCornerShape" not in content:
                        continue
                    if "BorderStroke" in imp and "BorderStroke" not in content:
                        continue
                    
                    new_imports.append(imp)

            if new_imports:
                # Find the last import
                parts = content.split("import ")
                if len(parts) > 1:
                    last_import_index = content.rfind("import ")
                    end_of_last_import = content.find("\n", last_import_index)
                    
                    if end_of_last_import != -1:
                        # Insert right after
                        insertion = "\n" + "\n".join(new_imports)
                        content = content[:end_of_last_import] + insertion + content[end_of_last_import:]
                else:
                    # just put it after package
                    pkg_idx = content.find("package ")
                    if pkg_idx != -1:
                        end_of_pkg = content.find("\n", pkg_idx)
                        insertion = "\n\n" + "\n".join(new_imports)
                        content = content[:end_of_pkg] + insertion + content[end_of_pkg:]
                    
            
            # Additional fixes
            # replace `elevation = ` with `shadowElevation = ` for Material3 Surface
            # wait, my manual check showed ORCareCard / ORCareGlassCard expects `elevation`.
            # If standard Card is used, CardDefaults.cardElevation is used. We probably shouldn't blindly replace `elevation =`
            
            # Replace old material imports
            content = re.sub(r"import androidx\.compose\.material\.(?!icons).*", "", content)
            
            with open(filepath, 'w', encoding='utf-8') as f:
                f.write(content)

print("Done fixing imports.")
