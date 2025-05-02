// filepath: u:\Java Programs\Java Final Game\Java-Game\Java-Game\Java Code\Inputs.java
import java.awt.event.*;

// Implement multiple interfaces for keyboard and mouse
public class Inputs implements KeyListener, MouseListener, MouseMotionListener {
    // Flags for button presses
    boolean wPressed = false;
    boolean aPressed = false;
    boolean sPressed = false;
    boolean dPressed = false;
    boolean spacePressed = false;

    // Mouse state
    boolean mouseLeftPressed = false; // Track left mouse button state
    boolean mouseRightPressed = false; // Track right mouse button state (optional)
    int mouseX = -1; // Current mouse X position relative to the component
    int mouseY = -1; // Current mouse Y position relative to the component
    int mouseClickX = -1; // Position of the last click X
    int mouseClickY = -1; // Position of the last click Y
    boolean mouseClicked = false; // Flag for a click event (press and release)

    // --- KeyListener Methods ---
    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        switch (keyCode) {
            case KeyEvent.VK_W: wPressed = true; break;
            // *** FIX: Correct variable assignments ***
            case KeyEvent.VK_A: aPressed = true; break; // Was wPressed
            case KeyEvent.VK_S: sPressed = true; break; // Was wPressed
            case KeyEvent.VK_D: dPressed = true; break; // Was wPressed
            case KeyEvent.VK_SPACE: spacePressed = true; break; // Was wPressed
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int keyCode = e.getKeyCode();
        switch (keyCode) {
            case KeyEvent.VK_W: wPressed = false; break;
            // *** FIX: Correct variable assignments ***
            case KeyEvent.VK_A: aPressed = false; break; // Was wPressed
            case KeyEvent.VK_S: sPressed = false; break; // Was wPressed
            case KeyEvent.VK_D: dPressed = false; break; // Was wPressed
            case KeyEvent.VK_SPACE: spacePressed = false; break; // Was wPressed
        }
    }

    @Override
    public void keyTyped(KeyEvent e) { /* Not used */ }

    // --- MouseListener Methods ---
    @Override
    public void mouseClicked(MouseEvent e) {
        // This event fires after a press and release without significant movement
        mouseClickX = e.getX();
        mouseClickY = e.getY();
        mouseClicked = true; // Set a flag that a click occurred
        System.out.println("Mouse clicked via Inputs at: (" + mouseClickX + ", " + mouseClickY + ")");
        // Note: mouseClicked flag should be reset by the game logic after processing
    }

    @Override
    public void mousePressed(MouseEvent e) {
        mouseX = e.getX(); // Update position on press
        mouseY = e.getY();
        if (e.getButton() == MouseEvent.BUTTON1) { // Left button
            mouseLeftPressed = true;
        } else if (e.getButton() == MouseEvent.BUTTON3) { // Right button
            mouseRightPressed = true;
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
         mouseX = e.getX(); // Update position on release
         mouseY = e.getY();
        if (e.getButton() == MouseEvent.BUTTON1) { // Left button
            mouseLeftPressed = false;
        } else if (e.getButton() == MouseEvent.BUTTON3) { // Right button
            mouseRightPressed = false;
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        // Called when the mouse cursor enters the component's bounds
        mouseX = e.getX();
        mouseY = e.getY();
    }

    @Override
    public void mouseExited(MouseEvent e) {
        // Called when the mouse cursor exits the component's bounds
        // Optional: Reset position or set a flag indicating mouse is outside
         mouseX = -1; // Indicate mouse is outside
         mouseY = -1;
    }

    // --- MouseMotionListener Methods ---
    @Override
    public void mouseDragged(MouseEvent e) {
        // Called when mouse button is pressed and mouse is moved
        mouseX = e.getX();
        mouseY = e.getY();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        // Called when mouse is moved without buttons pressed
        mouseX = e.getX();
        mouseY = e.getY();
    }

    // --- Getters for Key State ---
    public boolean isWPressed() { return wPressed; }
    public boolean isAPressed() { return aPressed; }
    public boolean isSPressed() { return sPressed; }
    public boolean isDPressed() { return dPressed; }
    public boolean isSpacePressed() { return spacePressed; }

    // --- Getters for Mouse State ---
    public boolean isMouseLeftPressed() { return mouseLeftPressed; }
    public boolean isMouseRightPressed() { return mouseRightPressed; }
    public int getMouseX() { return mouseX; }
    public int getMouseY() { return mouseY; }
    public int getMouseClickX() { return mouseClickX; }
    public int getMouseClickY() { return mouseClickY; }

    /**
     * Checks if a click occurred since the last check.
     * Resets the click flag.
     * @return true if a click happened, false otherwise.
     */
    public boolean consumeClick() {
        boolean click = this.mouseClicked;
        this.mouseClicked = false; // Reset flag after checking
        return click;
    }
}