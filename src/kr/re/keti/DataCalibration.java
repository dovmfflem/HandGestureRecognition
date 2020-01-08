package kr.re.keti;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;

import java.awt.Font;

import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.JButton;
import javax.swing.border.TitledBorder;
import javax.swing.border.EtchedBorder;

import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Arrays;

import javax.swing.JTextField;
import javax.swing.JSlider;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;

import kr.re.keti.sensorvalue.Port;
import kr.re.keti.sensorvalue.PortWorker;
import kr.re.keti.sensorvalue.Sensor;
import libsvm.svm;
import libsvm.svm_model;
import libsvm.svm_node;


public class DataCalibration {

	public static void main(String[] args) throws IOException, InterruptedException
	{
		final int baudRate = 912600;
		final String portName = "COM44";
		Port portWorker = new PortWorker(portName, baudRate);
		Sensor SensorsValue = new Sensor(portWorker);
		SensorsValue.openPort();

		int[] order = {4, 0, 1, 2, 3};
		int[] sensorNum = {5, 6, 6, 6, 6};
		int allOfPressure = 29; // 29 is 3th
//		int numOfSensingPoint=6+6+1;
		int numOfSensingPoint=6;
		int[] data = new int [allOfPressure];
		int k, i, j;

		System.out.printf("System Setting Finish\n");

		int[] max_data = new int [allOfPressure];
		int[] min_data = new int [allOfPressure];
		double[] data_normal = new double [allOfPressure];
		int data_count=0, data_countM=500;
		
		while(data_count < data_countM)
		{
			double[] result = SensorsValue.getDatas3th();
		
			if(result == null)
				continue;
			else if(result[0] == -12345.67890)
			{
//				updateGesture(10);
				continue;
			}

			k = 0;
//			System.out.printf("%d\t", data_count);
			if(data_count == 0)
			{
				System.out.printf("\npaper start\n");
				Thread.sleep(1000);
			}
			else if(data_count == data_countM/2)
			{
				System.out.printf("\ngrab start\n");
				Thread.sleep(1000);
			}
/*			else
			{
				System.out.printf(".");				
			}*/

			for(i = 0; i<5; i++)
			{
				for(j = 0; j<sensorNum[i]; j++)
				{
//					System.out.printf("%10.3f ", result[order[i]*numOfSensingPoint + j]);
//					System.out.printf("%d:%5.3f ", sk, svm_data[sk-1]);
					data[k++] = (int)result[order[i]*numOfSensingPoint + j];
					System.out.printf("%d:%d ", k, data[k-1]);
					if( data_count == 0 )
					{
						max_data[k-1] = data[k-1];
						min_data[k-1] = data[k-1];
					}
					if( max_data[k-1] < data[k-1] )
					{
						max_data[k-1] = data[k-1];
					}
					if( min_data[k-1] > data[k-1] )
					{
						min_data[k-1] = data[k-1];
					}
					
				}
/*
//				System.out.printf("%s\t", directionBuffer[(int)result[i*numOfSensingPoint+18]]);
//				System.out.printf("[%.1f]%5s\t", result[i*numOfSensingPoint+18], directionBuffer[(int)result[i*numOfSensingPoint+18]]);
				if (result[i*numOfSensingPoint+18] == 0)
					none_count ++;
				else 
					none_count = 0;
*/
			}
			
			data_count ++;
			System.out.println();
		}
		System.out.printf("Data Normalization Finish\n");
//		BufferedWriter output = new BufferedWriter(new FileWriter("data_normalization.txt"));
//		FileOutputStream output = new FileOutputStream("data_normalization.txt");
		FileWriter output = new FileWriter("data_normalization.txt");
		String max="", min="";
		for (i=0; i<allOfPressure; i++)
		{
//			System.out.printf("%d\t", max_data[i]);
//			System.out.printf("%d\n", min_data[i]);
			max += max_data[i] + "\t";
			min += min_data[i] + "\t";
		}
//		System.out.println("");
		max += "\n";
		min += "\n";
		output.write(max);
		output.flush();
		output.write(min);
		output.flush();
		output.close();
		
		
		while(true)
		{
			double[] result = SensorsValue.getDatas3th();
		
			if(result == null)
				continue;
			else if(result[0] == -12345.67890)
			{
//				updateGesture(10);
				continue;
			}

			k = 0;
			for(i = 0; i<5; i++)
			{
				for(j = 0; j<sensorNum[i]; j++)
				{
//					System.out.printf("%10.3f ", result[order[i]*numOfSensingPoint + j]);
//					System.out.printf("%d:%5.3f ", sk, svm_data[sk-1]);
					data[k] = (int)result[order[i]*numOfSensingPoint + j];
					data_normal[k] = (double)(data[k] - min_data[k]) / (double)(max_data[k] - min_data[k]) * 10.0;
					k++;
//					System.out.printf("%2.3f\t", data_normal[k-1]);
					System.out.printf("%2.3f\t", (double)data[k-1]/500.0);
				}
			}
			System.out.println();
		}
		
	}
	
}
