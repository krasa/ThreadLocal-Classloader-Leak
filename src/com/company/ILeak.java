package com.company;

public interface ILeak {

	ILeak getSingleton();

	byte[] getThreadLocal();

	void setThreadLocal(byte[] threadLocal);

	byte[] getStaticThreadLocal();

	void setStaticThreadLocal(byte[] staticThreadLocal);

	byte[] getLeakingThreadLocal();

	void setLeakingThreadLocal(byte[] leakingThreadLocal);

	byte[] getStaticLeakingThreadLocal();

	void setStaticLeakingThreadLocal(byte[] staticLeakingThreadLocal);

	byte[] getBytes();

	void setBytes(byte[] bytes);

	void newThreadLocal();

	void clearThreadLocal();

	void deleteThreadLocal();

	void newLeakingThreadLocal();

	void clearLeakingThreadLocal();

	void deleteLeakingThreadLocal();
}