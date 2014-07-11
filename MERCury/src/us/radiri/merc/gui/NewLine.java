package us.radiri.merc.gui;

/**
 * @author wessles
 */

public class NewLine extends Component {
    
    public NewLine() {
        this(32);
    }
    
    public NewLine(int height) {
        super("", 0, 0, 0, height);
        TYPE = TYPE_NONSPAN;
    }
}
