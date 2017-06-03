package jpass;

import jpass.ui.JPassFrame;
import jpass.util.Configuration;
import java.util.Optional;

public enum Singletons {
    I {
        private final Configuration configuration = new Configuration();
        private JPassFrame jPassFrame;
        @Override
        public Configuration getConfiguration() {
            return this.configuration;
        }
        @Override
        public JPassFrame getJPassFrame(String file) {
            if (jPassFrame == null) this.jPassFrame = new JPassFrame(file);
            return this.jPassFrame;
        }
        @Override
        public JPassFrame getJPassFrame() {
            if (jPassFrame == null) this.jPassFrame = new JPassFrame(null);
            return this.jPassFrame;
        }
    };
    public abstract Configuration getConfiguration();
    public abstract JPassFrame getJPassFrame(String file);
    public abstract JPassFrame getJPassFrame();
}