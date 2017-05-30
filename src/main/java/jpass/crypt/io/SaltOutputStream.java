package jpass.crypt.io;

import java.io.IOException;
import java.io.OutputStream;

/**
 * @since 19 May 2017
 * @author ryoji
 *
 */
public class SaltOutputStream extends OutputStream {
	private final OutputStream _parent;
	/**
	 * 
	 * @param outputStream
	 * @param salt byte[32]
	 * @throws IOException
	 */
	public SaltOutputStream(OutputStream outputStream, byte[] salt) throws IOException {
		outputStream.write(salt);
		this._parent = outputStream;
	}
	@Override
	public void write(int b) throws IOException {
		this._parent.write(b);
	}
}
