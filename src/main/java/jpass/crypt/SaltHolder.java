package jpass.crypt;

/**
 * @since 30 May 2017
 * @author ryoji
 */
public enum SaltHolder {
    INST {
        private byte[] salt;
        @Override
        public void setSalt(byte[] salt) {
            this.salt = salt;
        }

        @Override
        public byte[] getSalt() {
            return salt;
        }
    };
    public abstract void setSalt(byte[] salt);
    public abstract byte[] getSalt();
}
