package kz.aitu.bridge.client;

public final class WebUI {
    public static String getHtml() {
        return """
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Bridge Pattern</title>
    <style>
        @import url('https://fonts.googleapis.com/css2?family=Manrope:wght@400;500;700;800&display=swap');
        
        :root {
            --accent: #2159ff;
            --text-dark: #0a0b0d;
            --text-muted: #6b7280;
            --bg: #ffffff;
            --surface: #ffffff;
            --grid-color: #e5e7eb;
        }

        * { box-sizing: border-box; }
        
        body {
            margin: 0; padding: 0;
            font-family: 'Manrope', -apple-system, sans-serif;
            background: radial-gradient(circle at 30% 0%, #ffffff 0%, #f4f6fb 100%);
            color: var(--text-dark);
            min-height: 100vh;
            overflow-x: hidden;
            opacity: 0;
            animation: fadeIn 0.8s ease-out forwards;
        }
        
        @keyframes fadeIn {
            to { opacity: 1; }
        }

        header {
            display: flex; justify-content: space-between; align-items: center;
            padding: 30px 60px;
        }
        
        .logo { 
            font-size: 1.4rem; font-weight: 800; letter-spacing: -0.5px; 
            display: flex; align-items: center; gap: 4px; 
        }
        .logo span { color: var(--accent); font-size: 1.8rem; line-height: 0; position: relative; top: -3px; }
        
        .nav-links {
            display: flex; gap: 40px; font-size: 0.9rem; font-weight: 500; color: var(--text-muted);
        }
        
        .black-btn {
            background: #000; color: #fff; border-radius: 8px; padding: 12px 24px; 
            font-weight: 600; font-size: 0.9rem; border: none; cursor: pointer; 
            transition: 0.3s; box-shadow: 0 4px 14px rgba(0,0,0,0.1);
        }
        .black-btn:hover { background: #333; transform: scale(1.02); }

        .main-container {
            display: grid; grid-template-columns: 1fr 1fr; padding: 20px 60px 60px; gap: 80px; max-width: 1600px; margin: 0 auto;
        }

        /* LEFT SIDE */
        .left-side {
            display: flex; flex-direction: column; justify-content: center;
        }
        .headline {
            font-size: 5.5rem; font-weight: 800; line-height: 1.05; letter-spacing: -3px; 
            margin: 0 0 60px 0; color: #000;
        }

        .tools-grid {
            display: grid; grid-template-columns: 1fr 1fr 1fr; gap: 30px;
        }
        
        .tool-col { display: flex; flex-direction: column; }
        
        .icon-box {
            width: 52px; height: 52px; background: linear-gradient(135deg, #4f80ff, #1144ff);
            border-radius: 14px; margin-bottom: 24px; position: relative;
            box-shadow: 0 10px 30px rgba(17,68,255,0.4);
        }
        .icon-box::before {
            content: ''; position: absolute; width: 14px; height: 14px; background: #fff;
            border-radius: 50%; top: 50%; left: 50%; transform: translate(-50%, -50%);
            box-shadow: inset 0 2px 4px rgba(0,0,0,0.1);
        }
        .tool-col:nth-child(2) .icon-box {
            background: linear-gradient(135deg, #1aa3ff, #0055ff);
            box-shadow: 0 10px 30px rgba(0,85,255,0.3);
            border-radius: 16px 4px 16px 16px;
        }
        .tool-col:nth-child(3) .icon-box {
            background: linear-gradient(135deg, #a5b4f0, #738bf5);
            box-shadow: 0 10px 30px rgba(145,165,240,0.3);
            border-radius: 8px;
        }
        
        .tool-title { font-weight: 700; font-size: 1.1rem; margin-bottom: 8px; color: #000; }
        .tool-desc { font-size: 0.85rem; color: var(--text-muted); margin: 0 0 16px 0; line-height: 1.5; }
        
        .inputs-grid {
            display: grid; grid-template-columns: 1fr 1fr; gap: 10px; margin-bottom: 16px;
        }
        input, select {
            background: transparent; border: none; border-bottom: 1px solid #dcdcdc;
            padding: 4px; font-family: 'Manrope', sans-serif; font-size: 0.85rem; color: #333;
            outline: none; transition: 0.2s;
        }
        input::placeholder { color: #b0b0b0; }
        input:focus, select:focus { border-bottom-color: var(--accent); }
        
        .text-btn {
            background: transparent; border: none; color: var(--text-dark); font-weight: 700; 
            cursor: pointer; padding: 0; font-size: 0.9rem; text-align: left;
            transition: color 0.2s; align-self: flex-start;
        }
        .text-btn:hover { color: var(--accent); }

        /* RIGHT SIDE */
        .right-side {
            position: relative; padding-top: 20px;
        }
        
        .canvas-header {
            display: flex; align-items: flex-start; gap: 16px; margin-bottom: 20px;
        }
        .app-icon {
            width: 40px; height: 40px; background: #005aff; border-radius: 12px;
            display: flex; align-items: center; justify-content: center;
            box-shadow: 0 0 30px rgba(0, 90, 255, 0.4); 
            animation: pulse-glow 3s infinite alternate;
        }
        @keyframes pulse-glow {
            from { box-shadow: 0 0 20px rgba(0, 90, 255, 0.3); }
            to { box-shadow: 0 0 40px rgba(0, 90, 255, 0.6); }
        }
        .app-icon .dot { width: 12px; height: 12px; background: #fff; border-radius: 50%; box-shadow: inset 0 2px 4px rgba(0,0,0,0.2); }
        .canvas-header-text { margin-top: 4px; }
        .canvas-header-text span { font-size: 1rem; color: #000; font-weight: 600; font-style: italic; }
        .canvas-header-text p { margin: 4px 0 0 0; font-size: 0.85rem; color: var(--text-muted); }

        .canvas-wrapper {
            background: var(--surface);
            border-radius: 20px;
            padding: 6px;
            box-shadow: 0 20px 80px rgba(0,0,0,0.06);
            border: 1px solid rgba(255,255,255,0.8);
        }
        canvas {
            width: 100%; max-width: 800px; height: auto; aspect-ratio: 8 / 5;
            background-color: #f7f9fc;
            background-image: radial-gradient(var(--grid-color) 1px, transparent 1px);
            background-size: 24px 24px;
            border-radius: 14px;
            box-shadow: inset 0 0 40px rgba(0,0,0,0.02);
        }

        /* BOTTOM SECTION */
        .footer-bar {
            display: flex; justify-content: space-between; align-items: center;
            padding: 40px 60px; margin-top: 20px;
            border-top: 1px solid rgba(0,0,0,0.05);
            font-size: 0.9rem; font-weight: 500;
        }

        .partners { display: flex; align-items: center; gap: 30px; color: #999; }
        .shapes-nav { display: flex; gap: 30px; font-weight: 700; color: #000; }
        
        .list-section {
            padding: 0 60px 40px; display: grid; gap: 20px;
            grid-template-columns: repeat(auto-fill, minmax(280px, 1fr));
        }
        
        .shape-card { 
            background: transparent; border-top: 1px solid #eaeaea; padding: 20px 0;
            transition: 0.3s; display: flex; flex-direction: column; gap: 8px;
        }
        .shape-type { font-weight: 800; font-size: 1.1rem; color: #000; }
        .shape-details { font-size: 0.85rem; color: var(--text-muted); }
        .select-styled {
            border: 1px solid #ddd; background: #fff; border-radius: 6px; 
            padding: 4px 8px; font-size: 0.75rem; font-weight: 700; margin-top: 5px;
            max-width: 140px;
        }
    </style>
</head>
<body>

    <header>
        <div class="logo">BridgePattern<span>..</span></div>
        <div class="nav-links">
            <div>About</div>
            <div>Learn</div>
            <div>Support</div>
            <div>Blog</div>
        </div>
        <button class="black-btn" onclick="runDemo()">Run Demo</button>
    </header>

    <div class="main-container">
        <!-- LEFT TEXT & TOOLS -->
        <div class="left-side">
            <h1 class="headline">The Easiest<br>Way To Render</h1>
            
            <div class="tools-grid">
                <!-- Tool 1: Circle -->
                <div class="tool-col">
                    <div class="icon-box"></div>
                    <div class="tool-title">Vector/Raster</div>
                    <p class="tool-desc">Accessible and clear interface to create circles.</p>
                    <div class="inputs-grid">
                        <input id="cx" type="number" placeholder="X (200)" value="200">
                        <input id="cy" type="number" placeholder="Y (200)" value="200">
                        <input id="cr" type="number" placeholder="Radius (80)" value="80">
                        <select id="crend">
                            <option value="VECTOR">Vector</option>
                            <option value="RASTER">Raster</option>
                        </select>
                    </div>
                    <button class="text-btn" onclick="addCircle()">Add Circle &rarr;</button>
                </div>
                
                <!-- Tool 2: Square -->
                <div class="tool-col">
                    <div class="icon-box"></div>
                    <div class="tool-title">Balance</div>
                    <p class="tool-desc">Create absolute geometric squares effortlessly.</p>
                    <div class="inputs-grid">
                        <input id="sx" type="number" placeholder="X (400)" value="400">
                        <input id="sy" type="number" placeholder="Y (200)" value="200">
                        <input id="ss" type="number" placeholder="Size (120)" value="120">
                        <select id="srend">
                            <option value="VECTOR">Vector</option>
                            <option value="RASTER">Raster</option>
                        </select>
                    </div>
                    <button class="text-btn" onclick="addSquare()">Add Square &rarr;</button>
                </div>
                
                <!-- Tool 3: Actions -->
                <div class="tool-col">
                    <div class="icon-box"></div>
                    <div class="tool-title">Integration</div>
                    <p class="tool-desc">Manage your workspace by clearing embedded objects.</p>
                    <div style="height: 110px;"></div>
                    <button class="text-btn" onclick="clearShapes()">Clear Canvas &rarr;</button>
                </div>
            </div>
        </div>

        <!-- RIGHT CANVAS -->
        <div class="right-side">
            
            <div class="canvas-wrapper">
                <canvas id="view" width="800" height="500"></canvas>
            </div>
        </div>
    </div>

    <!-- BOTTOM BAR -->
    <div class="footer-bar">
        <div class="partners">
            Our patterns: &nbsp;&nbsp;&nbsp; <b>FACTORY</b> &nbsp;&nbsp; <b>STRATEGY</b> &nbsp;&nbsp; <b>ADAPTER</b>
        </div>
        <div class="shapes-nav">
            <div style="border-bottom: 2px solid #000; padding-bottom: 4px;">Current Shapes</div>
            <div style="color:#999;font-weight:500;">Version: 1.0.0</div>
        </div>
    </div>
    
    <div class="list-section" id="shapeList">
        <!-- Rendered via JS -->
    </div>

    <script>
        const canvas = document.getElementById('view');
        const ctx = canvas.getContext('2d');
        let shapes = [];

        async function fetchShapes() {
            const res = await fetch('/api/state');
            shapes = await res.json();
            renderList();
            draw();
        }

        async function runAction(action, params = {}) {
            const qs = new URLSearchParams({ action, ...params }).toString();
            await fetch('/api/action?' + qs, { method: 'POST' });
            fetchShapes();
        }

        function addCircle() {
            runAction('createCircle', {
                x: document.getElementById('cx').value || 200,
                y: document.getElementById('cy').value || 200,
                radius: document.getElementById('cr').value || 80,
                renderer: document.getElementById('crend').value
            });
        }

        function addSquare() {
            runAction('createSquare', {
                x: document.getElementById('sx').value || 400,
                y: document.getElementById('sy').value || 200,
                side: document.getElementById('ss').value || 120,
                renderer: document.getElementById('srend').value
            });
        }

        function switchRenderer(index, evt) {
            runAction('switch', { index, renderer: evt.target.value });
        }

        function runDemo() { runAction('demo'); }
        function clearShapes() { runAction('clear'); }

        function renderList() {
            const list = document.getElementById('shapeList');
            list.innerHTML = '';
            shapes.forEach((s, i) => {
                const item = document.createElement('div');
                item.className = 'shape-card';
                item.innerHTML = `
                    <div class="shape-type">${s.type === 'circle' ? 'Circle' : 'Square'}</div>
                    <div class="shape-details">Coordinates: x = ${s.x}, y = ${s.y}<br>Size: ${s.size}</div>
                    <select class="select-styled" onchange="switchRenderer(${i}, event)">
                        <option value="VECTOR" ${s.renderer === 'VECTOR' ? 'selected' : ''}>VECTOR</option>
                        <option value="RASTER" ${s.renderer === 'RASTER' ? 'selected' : ''}>RASTER</option>
                    </select>
                `;
                list.appendChild(item);
            });
        }

        function draw() {
            ctx.clearRect(0, 0, canvas.width, canvas.height);
            
            shapes.forEach(shape => {
                const isVector = shape.renderer === 'VECTOR';
                ctx.save();
                
                if (isVector) {
                    // Vector: Smooth solid lines, elegant blue
                    ctx.strokeStyle = '#2159ff';
                    ctx.lineWidth = 2.5;
                    ctx.beginPath();
                    if (shape.type === 'circle') {
                        ctx.arc(shape.x, shape.y, shape.size, 0, Math.PI * 2);
                    } else if (shape.type === 'square') {
                        ctx.rect(shape.x - shape.size/2, shape.y - shape.size/2, shape.size, shape.size);
                    }
                    ctx.stroke();
                    ctx.fillStyle = 'rgba(33, 89, 255, 0.04)';
                    ctx.fill();
                    
                    // Center anchor
                    ctx.beginPath();
                    ctx.arc(shape.x, shape.y, 4, 0, Math.PI*2);
                    ctx.fillStyle = '#0a0b0d';
                    ctx.fill();

                } else {
                    // Raster: Grayscale chunky pixels (matching the tech-minimalist vibe)
                    ctx.fillStyle = '#222831';
                    const pixelSize = 6;
                    const boundsSize = shape.size * 1.25;
                    let startX = shape.x - boundsSize;
                    let startY = shape.y - boundsSize;
                    let endX = shape.x + boundsSize;
                    let endY = shape.y + boundsSize;

                    for(let px = startX; px < endX; px += pixelSize) {
                        for(let py = startY; py < endY; py += pixelSize) {
                            if (shape.type === 'circle') {
                                const dist = Math.hypot(px - shape.x, py - shape.y);
                                if (dist <= shape.size) {
                                    ctx.fillRect(px, py, pixelSize - 1, pixelSize - 1);
                                }
                            } else if (shape.type === 'square') {
                                const half = shape.size / 2;
                                if (Math.abs(px - shape.x) <= half && Math.abs(py - shape.y) <= half) {
                                    ctx.fillRect(px, py, pixelSize - 1, pixelSize - 1);
                                }
                            }
                        }
                    }
                }
                ctx.restore();
            });
        }
        
        // Init
        fetchShapes();
    </script>
</body>
</html>
""";
    }
}