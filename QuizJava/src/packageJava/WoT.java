package packageJava;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Scanner;

public class WoT {
	final static String[] tankArray = new String[360];
	final static int[] keyArray = new int[360];
	public static void main(String[] args) throws IOException {
		readFile();
		int tank = 1;
		while (tank != 0) {
			@SuppressWarnings("resource")
			Scanner sc = new Scanner(System.in);
			System.out.println("");
			System.out.print("Your tank choice (number) [あなたのタンクの選択（数）]： ");
			tank = sc.nextInt();
			if (tank == 0) {
				System.out.print("You exited the program. [あなたはプログラムを終了しました。]");
				System.exit(0);
			}
			System.out.println("You entered " + keyArray[tank] + ", you chose " + tankArray[tank] + ". [あなたが入力した" + keyArray[tank]  + "、 あなたが選びました" + tankArray[tank] + "。]");
			System.out.print("How much damage did you do? [どれくらいのダメージ？]： ");
			int damage = sc.nextInt();
			System.out.print("How much assist damage did you get? [どのくらいの被害を助けた？]： ");
			int assistDamage = sc.nextInt();
			System.out.print("How much FF (Friendly Fire) did you do? [どれくらいFF（フレンドリーファイア）でしたか？]： ");
			int FF = sc.nextInt();
			System.out.print("Enter your current MoE%: [現在のMoE％を入力してください]： ");
			double currentMoE = sc.nextDouble();
			double MoECalc;
			double expectedDamage = expectedDamageIndex(tank);
			//MoE progress calculation
			if (currentMoE >= 0 && currentMoE <= 64.99) {
				currentMoE /= 100;
				MoECalc = (((damage + assistDamage - FF) / (expectedDamage * (1 + currentMoE))) * 0.65) - 1;
				currentMoE *= 100;
			}
			else if (currentMoE >= 65 && currentMoE <= 84.99) {
				currentMoE /= 100;
				MoECalc = (((damage + assistDamage - FF) / (expectedDamage * (1 + currentMoE))) * 0.85) - 1;
				currentMoE *= 100;
			}
			else if (currentMoE >= 85 && currentMoE <= 94.99) {
				currentMoE /= 100;
				MoECalc = (((damage + assistDamage - FF) / (expectedDamage * (1 + currentMoE))) * 0.95) - 1;
				currentMoE *= 100;
			}
			else {
				currentMoE /= 100;
				MoECalc = (((damage + assistDamage - FF) / (expectedDamage * (1 + currentMoE))) * 1.00) - 1;
				currentMoE *= 100;
			}
			double newMoE = currentMoE + MoECalc;
			DecimalFormat df = new DecimalFormat("#.##");
			df.setRoundingMode(RoundingMode.HALF_UP);
			System.out.println("Your MoE changed from " + currentMoE + "% to " + df.format(newMoE) + "%");
			System.out.println("あなたのMoEはから変更されました" + currentMoE + "％に" + df.format(newMoE) + "％");
			double newMoEdouble = Double.parseDouble(df.format(newMoE));
			if (currentMoE > newMoEdouble) {
				System.out.println("Your MoE decreased by: " + df.format(MoECalc) + "%");
				System.out.println("あなたのMoEは以下によって減少しました：" + df.format(MoECalc) + "％");
			}
			else {
				System.out.println("Your MoE increased by: " + df.format(MoECalc) + "%");
				System.out.println("あなたのMoEは次のように増加しました： " + df.format(MoECalc) + "％");
			}
		}
	}
	
	//This code block reads tankList.txt for all tanks in WoT.
	//このコードブロックは、WoT内のすべてのタンクのtankList.txtを読み取ります。
	public static void readFile() throws IOException {
		File file = new File("C://Users/User/Desktop/tankList.txt");
		if (file.exists()) {
			FileReader fr = new FileReader(file);
			BufferedReader br = new BufferedReader(fr);	
			for (int i = 0; i < 360; i++) {
				tankArray[i] = br.readLine();
			}
			br.close();
			for (int i = 0; i < keyArray.length; i++) {
		    	keyArray[i] = i;
		    	System.out.println(keyArray[i] + " = " + tankArray[i]);
		    }
		}
		else {
			System.out.print("tankList.txt is missing. Please insert tankList.txt into the same location as WoT.java");
			System.exit(0);
		}
	}
	
	//This code block sets the expected damage for all tanks. (Source: http://wotlabs.net/sea/wnTable)
	//このコードブロックは、すべてのタンクに予想されるダメージを設定します。 （出典：http://wotlabs.net/sea/wnTable）
	public static double expectedDamageIndex (int tank) {
		double expectedDamage = 0;
		//T10 LT
		if (tank >= 1 && tank <= 5) {
			expectedDamage = 1400.00;
		}	
		//T10 MT
		else if (tank >= 6 && tank <= 23) {
			expectedDamage = 1882.27;
		}
		//T10 HT
		else if (tank >= 24 && tank <= 39) {
			expectedDamage = 1918.96;	
		}
		//T10 TD
		else if (tank >= 40 && tank <= 49) {
			expectedDamage = 2153.40;
		}
		//T10 SPG
		else if (tank >= 50 && tank <= 54) {
			expectedDamage = 1770.55;
		}
		//T9 LT
		else if (tank >= 55 && tank <= 59) {
			expectedDamage = 1100.00;
		}
		//T9 MT
		else if (tank >= 60 && tank <= 72) {
			expectedDamage = 1540.28;
		}
		//T9 HT
		else if (tank >= 73 && tank <= 83) {
			expectedDamage = 1579.22;
		}
		//T9 TD
		else if (tank >= 84 && tank <= 93) {
			expectedDamage = 1719.84;
		}
		//T9 SPG
		else if (tank >= 94 && tank <= 98) {
			expectedDamage = 1525.78;
		}
		//T8 LT
		else if (tank >= 99 && tank <= 104) {
			expectedDamage = 860.12;
		}
		//T8 MT
		else if (tank >= 105 && tank <= 134) {
			expectedDamage = 1162.12;
		}
		//T8 HT
		else if (tank >= 135 && tank <= 167) {
			expectedDamage = 1283.61;
		}
		//T8 TD
		else if (tank >= 168 && tank <= 182) {
			expectedDamage = 1369.06;
		}
		//T8 SPG
		else if (tank >= 184 && tank <= 187) {
			expectedDamage = 1295.90;
		}
		//T7 LT
		else if (tank >= 188 && tank <= 194) {
			expectedDamage = 702.70;
		}
		//T7 MT
		else if (tank >= 195 && tank <= 207) {
			expectedDamage = 928.14;
		}
		//T7 HT
		else if (tank >= 208 && tank <= 220) {
			expectedDamage = 1106.68;
		}
		//T7 TD
		else if (tank >= 221 && tank <= 237) {
			expectedDamage = 1053.92;
		}
		//T7 SPG
		else if (tank >= 238 && tank <= 243) {
			expectedDamage = 1094.53;			
		}
		//T6 LT
		else if (tank >= 244 && tank <= 250) {
			expectedDamage = 511.48;
		}
		//T6 MT
		else if (tank >= 251 && tank <= 273) {
			expectedDamage = 745.25;
		}
		//T6 HT
		else if (tank >= 274 && tank <= 285) {
			expectedDamage = 832.30;
		}
		//T6 TD
		else if (tank >= 286 && tank <= 297) {
			expectedDamage = 802.78;
		}
		//T6 SPG
		else if (tank >= 298 && tank <= 302) {
			expectedDamage = 892.83;
		}
		//T5 LT
		else if (tank >= 303 && tank <= 308) {
			expectedDamage = 393.22;
		}
		//T5 MT
		else if (tank >= 309 && tank <= 330) {
			expectedDamage = 513.45;
		}
		//T5 HT
		else if (tank >= 331 && tank <= 342) {
			expectedDamage = 657.37;
		}
		//T5 TD
		else if (tank >= 343 && tank <= 353) {
			expectedDamage = 605.39;
		}
		//T5 SPG
		else if (tank >= 354 && tank <= 359) {
			expectedDamage = 653.26;
		}
		return expectedDamage;
	}
}
