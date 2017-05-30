package jpass.crypt.io;

import java.io.IOException;
import java.io.InputStream;

/**
 * @since 19 May 2017
 * @author ryoji
 *
 */
public class SaltInputStream extends InputStream {
    private final InputStream _parent;
	public SaltInputStream(InputStream inputStream) throws IOException {
		inputStream.read(new byte[36]);
		this._parent = inputStream;
	}
	@Override
	public int read() throws IOException {
		return _parent.read();
	}
}