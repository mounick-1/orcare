const os = require('os');
const fs = require('fs');
const interfaces = os.networkInterfaces();
let output = 'Network Interfaces:\n';
for (const name of Object.keys(interfaces)) {
    output += `\n[${name}]\n`;
    for (const iface of interfaces[name]) {
        output += `  ${iface.family} ${iface.address} (Internal: ${iface.internal})\n`;
    }
}
fs.writeFileSync('all_ips_debug.txt', output);
console.log('Results written to all_ips_debug.txt');
