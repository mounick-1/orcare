const fs = require('fs');
const path = require('path');

const directory = path.join('app', 'src', 'main', 'java', 'com', 'simats', 'orcare', 'ui');

const importsToEnsure = [
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
];

function processDirectory(dir) {
    fs.readdirSync(dir).forEach(file => {
        const filepath = path.join(dir, file);
        if (fs.statSync(filepath).isDirectory()) {
            processDirectory(filepath);
        } else if (file.endsWith('.kt')) {
            processFile(filepath);
        }
    });
}

function processFile(filepath) {
    let content = fs.readFileSync(filepath, 'utf8');
    const newImports = [];

    for (const imp of importsToEnsure) {
        if (!content.includes(imp)) {
            // Simplistic check for some before adding blind import
            if (imp.includes('delay') && !content.includes('delay(')) continue;
            if (imp.includes('TextAlign') && !content.includes('TextAlign.')) continue;
            if (imp.includes('CircleShape') && !content.includes('CircleShape')) continue;
            if (imp.includes('RoundedCornerShape') && !content.includes('RoundedCornerShape')) continue;
            if (imp.includes('BorderStroke') && !content.includes('BorderStroke')) continue;
            newImports.push(imp);
        }
    }

    let modified = false;

    if (newImports.length > 0) {
        const lastImportIndex = content.lastIndexOf('import ');
        if (lastImportIndex !== -1) {
            const endOfLastImport = content.indexOf('\n', lastImportIndex);
            if (endOfLastImport !== -1) {
                const insertion = '\n' + newImports.join('\n');
                content = content.substring(0, endOfLastImport) + insertion + content.substring(endOfLastImport);
                modified = true;
            }
        } else {
            const pkgIdx = content.indexOf('package ');
            if (pkgIdx !== -1) {
                const endOfPkg = content.indexOf('\n', pkgIdx);
                const insertion = '\n\n' + newImports.join('\n');
                content = content.substring(0, endOfPkg) + insertion + content.substring(endOfPkg);
                modified = true;
            }
        }
    }

    // Replace old material imports
    const regex = /import androidx\.compose\.material\.(?!icons).*\n/g;
    if (regex.test(content)) {
        content = content.replace(regex, '');
        modified = true;
    }

    // Fix `LaunchedEffect` scope if missing import or incorrect
    // Missing LaunchedEffect import is covered by `runtime.*` which we add globally anyway. Wait! I should ensure `runtime.*` is fully added.

    if (modified) {
        fs.writeFileSync(filepath, content, 'utf8');
        console.log(`Updated ${filepath}`);
    }
}

processDirectory(directory);
console.log('Done fixing imports.');
