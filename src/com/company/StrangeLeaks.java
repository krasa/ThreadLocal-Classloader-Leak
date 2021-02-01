package com.company;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;

public class StrangeLeaks {

	public static void main(String[] args) throws Exception {
		leak2();
		System.gc();
		System.err.println(new byte[20_000_000].length);
		System.gc();
		System.err.println(new byte[20_000_000].length);
		System.gc();
		System.err.println(new byte[20_000_000].length);
		System.gc();
		System.err.println(new byte[20_000_000].length);
		System.gc();
		System.err.println(new byte[20_000_000].length);
		System.gc();
		System.err.println(new byte[20_000_000].length);
		System.gc();
		System.err.println(new byte[20_000_000].length);
		System.gc();
		System.err.println(new byte[20_000_000].length);
		System.gc();

	// leaked classloader will cause heapdump to be 20+MB
		new Main.HeapDump().dumpHeap("HeapDump-" + System.currentTimeMillis() + ".hprof", true);
	}

	private static void leak() throws Exception {
		File file = new File("lib/leakingModule.jar");

		URLClassLoader urlClassLoader1 = new URLClassLoader("foo", new URL[]{file.toURI().toURL()}, StrangeLeaks.class.getClassLoader());

		ILeak leak = (ILeak) Class.forName("my.Leak", true, urlClassLoader1).getConstructor().newInstance();

		leak.setLeakingThreadLocal(new byte[1_000_000]);
		//causes leak
//		leak.newLeakingThreadLocal();


		URLClassLoader urlClassLoader = urlClassLoader1;
		urlClassLoader.close();
	}

	private static void leak2() throws Exception {
		File file = new File("lib/leakingModule.jar");

		URLClassLoader urlClassLoader1 = new URLClassLoader("foo", new URL[]{file.toURI().toURL()}, StrangeLeaks.class.getClassLoader());

		ILeak leak = (ILeak) Class.forName("my.Leak", true, urlClassLoader1).getConstructor().newInstance();
		System.err.println(leak);

		//does not leak, because the variable is nowhere referenced, unless newThreadLocal is called
		leak.setLeakingThreadLocal(new byte[1_000_000]);

		//leaks
//		leak.setStaticLeakingThreadLocal(new byte[1_000_000]);
//		
//		
		leak.setStaticThreadLocal(new byte[1_000_000]);
		leak.setThreadLocal(new byte[1_000_000]);


		leak.newThreadLocal();
//		leak.newThreadLocal();
//		leak.newThreadLocal();
//		leak.newThreadLocal();
//		leak.setThreadLocal(new byte[1_000_000]);

//		leak.clearThreadLocal();

		ILeak singleton = leak.getSingleton();
		System.err.println(singleton);

		//leaks
//		singleton.setLeakingThreadLocal(new byte[1_000_000]);

//		singleton.setBytes(new byte[1_000_000]);
//		singleton.setThreadLocal(new byte[1_000_000]);
		URLClassLoader urlClassLoader = urlClassLoader1;
		urlClassLoader.close();
	}

}
