package my;

import com.company.ILeak;

import java.util.Arrays;
import java.util.StringJoiner;

public class Leak implements ILeak {
	static int i = 0;
	
	public ThreadLocal<byte[]> threadLocal = new ThreadLocal<>();
	public static ThreadLocal<byte[]> staticThreadLocal = new ThreadLocal<>();

	public ThreadLocal<ILeak> leakingThreadLocal = new ThreadLocal<>();
	public static ThreadLocal<ILeak> staticLeakingThreadLocal = new ThreadLocal<>();

	public static byte[] staticBytes = new byte[20_000_000];

	public byte[] bytes;

	private final static Leak singleton = new Leak();
	private int counter;

	@Override
	public ILeak getSingleton() {
		return singleton;
	}

	public Leak() {
		counter = ++i;
	}

	public Leak(byte[] bytes) {
		this.bytes = bytes;
		counter = ++i;
	}

	public byte[] getThreadLocal() {
		return threadLocal.get();
	}

	public void setThreadLocal(byte[] bytes) {
		this.threadLocal.set(bytes);
	}

	public byte[] getStaticThreadLocal() {
		return staticThreadLocal.get();
	}

	public void setStaticThreadLocal(byte[] bytes) {
		staticThreadLocal.set(bytes);
	}

	public byte[] getLeakingThreadLocal() {
		return leakingThreadLocal.get().getBytes();
	}

	public void setLeakingThreadLocal(byte[] bytes) {
		this.leakingThreadLocal.set(new Leak(bytes));
	}

	public byte[] getStaticLeakingThreadLocal() {
		return staticLeakingThreadLocal.get().getBytes();
	}

	public void setStaticLeakingThreadLocal(byte[] bytes) {
		staticLeakingThreadLocal.set(new Leak(bytes));
	}

	public byte[] getBytes() {
		return bytes;
	}

	public void setBytes(byte[] bytes) {
		this.bytes = bytes;
	}

	@Override
	public void newThreadLocal() {
		threadLocal = new ThreadLocal<>();
	}

	@Override
	public void clearThreadLocal() {
		threadLocal.remove();
	}

	@Override
	public void deleteThreadLocal() {
		threadLocal = null;
	}
	
	@Override
	public void newLeakingThreadLocal() {
		leakingThreadLocal = new ThreadLocal<>();
	}

	@Override
	public void clearLeakingThreadLocal() {
		leakingThreadLocal.remove();
	}

	@Override
	public void deleteLeakingThreadLocal() {
		leakingThreadLocal = null;
	}

	@Override
	public String toString() {
		return new StringJoiner(", ", Leak.class.getSimpleName() + "[", "]")
				.add("threadLocal=" + threadLocal)
				.add("leakingThreadLocal=" + leakingThreadLocal)
				.add("bytes=" + Arrays.toString(bytes))
				.add("counter=" + counter)
				.toString();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Leak leak = (Leak) o;

		return counter == leak.counter;
	}

	@Override
	public int hashCode() {
		return counter;
	}
}
