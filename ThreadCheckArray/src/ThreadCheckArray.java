import java.util.ArrayList;

/**
 * solves the subset sum problem using recursive backtracking in a multiple thread environment.
 */
public class ThreadCheckArray implements Runnable 
{
	private boolean flag;
	private boolean [] winArray;
	SharedData sd;
	ArrayList<Integer> arrayList;
	int b;
	
	/**
	 * @param sd Shared data container for inputs and results.
	 */
	public ThreadCheckArray(SharedData sd) 
	{
		this.sd = sd;	
		synchronized (sd) 
		{
			arrayList = sd.getArrayList();
			b = sd.getB();
		}		
		winArray = new boolean[arrayList.size()];
	}
	
	/**
	 * Recursive search for a subset that sums to {@code b}.
	 * @param n Current element index.
	 * @param b Remaining target sum.
	 */
	void rec(int n, int b)
	{
		synchronized (sd) 
		{
			if (sd.getFlag())
				return;
		}	
		if (n == 1)
		{
			if(b == 0 || b == arrayList.get(n-1))
			{
				flag = true;
				synchronized (sd) 
				{
					sd.setFlag(true);
				}			
			}
			if (b == arrayList.get(n-1))
				winArray[n-1] = true;
			return;
		}
		
		rec(n-1, b - arrayList.get(n-1));
		if (flag)
			winArray[n-1] = true;
		synchronized (sd) 
		{
			if (sd.getFlag())
				return;
		}	
		rec(n-1, b);
	}

	/**
	 * Entry point, splits search space between thread1 (include last) and others (exclude last).
	 */
	public void run() {
		if (arrayList.size() != 1)
			if (Thread.currentThread().getName().equals("thread1"))
				rec(arrayList.size()-1, b - arrayList.get(arrayList.size() - 1));
			else 
				rec(arrayList.size()-1, b);
		if (arrayList.size() == 1)
			if (b == arrayList.get(0) && !flag)
			{
				winArray[0] = true;
				flag = true;
				synchronized (sd) 
				{
					sd.setFlag(true);
				}
			}
		if (flag)
		{
			if (Thread.currentThread().getName().equals("thread1"))
				winArray[arrayList.size() - 1] = true;
			synchronized (sd) 
			{
				sd.setWinArray(winArray);
			}	
		}
	}
}
