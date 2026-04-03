import java.util.Scanner;
import java.util.ArrayList;

/**
 * runs the program
 * reads input from the user and creates threads
 * and prints the result based on the calculation
 */
public class TestThreadCheckArray {
	public static void main(String[] args) {
		try (Scanner input = new Scanner(System.in)) {
			Thread thread1, thread2;
			System.out.println("Enter array size");
			int num  = input.nextInt();
            ArrayList<Integer> arrayList = new ArrayList<>();
			System.out.println("Enter numbers for array");
			
			for (int index = 0; index < num; index++) 
                arrayList.add(input.nextInt());
			
			System.out.println("Enter number");
			num = input.nextInt();
			
			SharedData sd = new SharedData(arrayList, num);
			/**
             * creates and starts threads to process the data
             */
			thread1 = new Thread(new ThreadCheckArray(sd), "thread1");
			thread2 = new Thread(new ThreadCheckArray(sd), "thread2");
			thread1.start();
			thread2.start();
			
			/**
	        * waits for both threads to finish
	        */
			try 
			{
				
				thread1.join();
				thread2.join();
			} 
			catch (InterruptedException e)
			{
				e.printStackTrace();
			}
			/**
             * checks if a solution was found
             */
			if (!sd.getFlag())
			{
				System.out.println("Sorry");
				return;
			}
            System.out.println("Solution for b : " + sd.getB() + ",n = " + sd.getArrayList().size());
            /**
             * prints indexes
             */
            System.out.print("I:    ");
            for (int index = 0; index < sd.getArrayList().size(); index++)
				System.out.print(index + "    ");
			System.out.println();
			System.out.print("A:    ");
            for (int value : sd.getArrayList()) {
                System.out.print(value);
                int counter = 5;
                int temp = value;
                while (true) {
                    temp = temp / 10;
                    counter--;
                    if (temp == 0)
                        break;
                }
                for (int i = 0; i < counter; i++)
                    System.out.print(" ");
            }

            System.out.println();
            System.out.print("C:    ");
            for (boolean index : sd.getWinArray()) {
                if (index)
                    System.out.print("1    ");
                else
                    System.out.print("0    ");
            }
        }
    }
}
