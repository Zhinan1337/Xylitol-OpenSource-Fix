//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Administrator\Downloads\Minecraft1.12.2 Mappings"!

//Decompiled by Procyon!

package shop.xiaoda.utils.system;

import java.util.*;

public class MemoryUtils
{
    public static void optimizeMemory(final long cleanUpDelay) {
        try {
            System.gc();
            System.runFinalization();
            final List<Thread> threads = new ArrayList<Thread>();
            for (int availableProcessors = Runtime.getRuntime().availableProcessors(), i = 0; i < availableProcessors; ++i) {
                final Thread thread = new Thread(() -> {
                    try {
                        Thread.sleep(cleanUpDelay);
                    }
                    catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    return;
                });
                threads.add(thread);
                thread.start();
            }
            final Iterator<Thread> iterator = threads.iterator();
            while (iterator.hasNext()) {
                final Thread thread = iterator.next();
                thread.join();
            }
        }
        catch (InterruptedException e2) {
            e2.printStackTrace();
        }
    }
    
    public static void memoryCleanup() {
        System.gc();
    }
}
