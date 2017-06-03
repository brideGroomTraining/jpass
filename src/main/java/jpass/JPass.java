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

package jpass;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.plaf.ColorUIResource;
import javax.swing.plaf.metal.DefaultMetalTheme;
import javax.swing.plaf.metal.MetalLookAndFeel;
import org.apache.commons.lang.StringUtils;
import jpass.util.Configuration;

/**
 * Entry point of JPass.
 *
 * @author Gabor_Bata
 *
 */
public class JPass {
    private static final Configuration configuration = Singletons.I.getConfiguration();
    private static final String METAL_LOOK_AND_FEEL = "javax.swing.plaf.metal.MetalLookAndFeel";
    public static void main(final String[] args) {
        try {
            final String lookAndFeel = ((configuration.is("system.look.and.feel.enabled", false)))
            		                 ? UIManager.getSystemLookAndFeelClassName() 
            		                 : METAL_LOOK_AND_FEEL;
            if (StringUtils.equals(lookAndFeel, METAL_LOOK_AND_FEEL)) {
                MetalLookAndFeel.setCurrentTheme(new myMetalTheme());
                UIManager.put("swing.boldMetal", Boolean.FALSE);
            }
            UIManager.setLookAndFeel(lookAndFeel);
        } catch (Exception e) {
            e.printStackTrace();
        }
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                Singletons.I.getJPassFrame((args.length > 0) ? args[0] : null);
            }
        });
    }
}
class myMetalTheme extends DefaultMetalTheme {
    @Override
    protected ColorUIResource getPrimary1() {
        return new ColorUIResource(0x4d6781);
    }
    @Override
    protected ColorUIResource getPrimary2() {
        return new ColorUIResource(0x7a96b0);
    }
    @Override
    protected ColorUIResource getPrimary3() {
        return new ColorUIResource(0xc8d4e2);
    }
    @Override
    protected ColorUIResource getSecondary1() {
        return new ColorUIResource(0x000000);
    }
    @Override
    protected ColorUIResource getSecondary2() {
        return new ColorUIResource(0xaaaaaa);
    }
    @Override
    protected ColorUIResource getSecondary3() {
        return new ColorUIResource(0xdfdfdf);
    }
}
