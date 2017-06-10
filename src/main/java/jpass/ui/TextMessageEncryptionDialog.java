/*
 * JPass
 *
 * Copyright (c) 2009-2017 Gabor Bata
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 * 1. Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in the
 *    documentation and/or other materials provided with the distribution.
 * 3. The name of the author may not be used to endorse or promote products
 *    derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE AUTHOR ``AS IS'' AND ANY EXPRESS OR
 * IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
 * IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR ANY DIRECT, INDIRECT,
 * INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT
 * NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 * THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF
 * THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package jpass.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.Optional;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;

/**
 * A dialog to encrypt text message.
 * This java code was copied mainly Gabor_Bata's EntryDialog.java
 * @author Gabor_Bata and Ryoji
 * @since 10 June 2017
 */
public class TextMessageEncryptionDialog extends JDialog implements ActionListener {
    private static final long serialVersionUID = -5202297177980732986L;
    private String      text;
    private JPanel      notesPanel;
    private JPanel      buttonPanel;
    private JTextArea   notesField;
    private JButton     okButton;
    private JButton     cancelButton;
    /**
     * Creates a new TextMessageEncryptionDialog instance.
     * @param parent parent component
     */
    public TextMessageEncryptionDialog(final JPassFrame parent, final String title) {
        super(parent, title, true);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        this.notesPanel = new JPanel(new BorderLayout(5, 5));
        this.notesPanel.setBorder(new EmptyBorder(0, 5, 0, 5));
        this.notesField = TextComponentFactory.newTextArea();
        this.notesField.setFont(TextComponentFactory.newTextField().getFont());
        this.notesField.setLineWrap(true);
        this.notesField.setWrapStyleWord(true);
        this.notesPanel.add(new JScrollPane(this.notesField), BorderLayout.CENTER);
        this.buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        this.okButton = new JButton("OK", MessageDialog.getIcon("accept"));
        this.okButton.setActionCommand("ok_button");
        this.okButton.setMnemonic(KeyEvent.VK_O);
        this.okButton.addActionListener(this);
        this.buttonPanel.add(this.okButton);
        this.cancelButton = new JButton("Cancel", MessageDialog.getIcon("cancel"));
        this.cancelButton.setActionCommand("cancel_button");
        this.cancelButton.setMnemonic(KeyEvent.VK_C);
        this.cancelButton.addActionListener(this);
        this.buttonPanel.add(this.cancelButton);
        getContentPane().add(this.notesPanel, BorderLayout.CENTER);
        getContentPane().add(this.buttonPanel, BorderLayout.SOUTH);
        setSize(560, 400);
        setMinimumSize(new Dimension(560, 400));
        setLocationRelativeTo(parent);
        setVisible(true);
    }

    /**
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        if ("ok_button".equals(command)) {
            setText(notesField.getText());
            dispose();
        } else if ("cancel_button".equals(command)) {
            dispose();
        }
    }

    /**
     * Sets the text data.
     */
    private void setText(String text) {
        this.text = text;
    }

    /**
     * Gets the text data of this dialog.
     */
    public Optional<String> getText() {
        return Optional.ofNullable(this.text);
    }
}