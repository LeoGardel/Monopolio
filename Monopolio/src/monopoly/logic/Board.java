package monopoly.logic;

import java.util.ArrayList;

import java.io.IOException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileNotFoundException;

import monopoly.camera.CameraHandler;
import monopoly.objects.InanimatedElement;
import monopoly.objects.InanimatedObject;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector3;

public class Board
{
	private float borderLimit = 4.3f;
	
	InanimatedObject boardModel;
	InanimatedElement boardNode;
	
	private static Board sharedInstance;
	
	public  ArrayList<Space> spaces = new ArrayList<Space>();
	
	public static Board getSharedInstance()
	{
		if (sharedInstance == null)
		{
			sharedInstance = new Board();
		}
		return sharedInstance;
	}
	
	public Board()
	{
		loadModel();
		 
	}
	
		
	private void loadModel()
	{
		try
		{
			boardModel = new InanimatedObject("board/board", "board/board3.png", false);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		
		boardNode = new InanimatedElement(boardModel);
	}
	
	
	public void initSpaces()
	{
		// Initializing board spaces
		File file = new File("data/neighborhood_data.txt");
        BufferedReader reader = null;
        
        File file2 = new File("data/company_data.txt");
        BufferedReader reader2 = null;
 
        try 
        {
            reader = new BufferedReader(new FileReader(file));
            String text = null;
 
            // repeat until all lines is read
            while ((text = reader.readLine()) != null) 
            {
            	// Excluding first line
            	if (!text.contains("#"))
            	{
            		String[] strArray = text.split(" "); 
            		int number = Integer.parseInt(strArray[0]);
            		
            		if(number == 5 || number == 12 || number == 15 || number == 25 || number == 28 || number == 35){
            			Company company = new Company(strArray[1], strArray[2], strArray[3], strArray[4], strArray[0]);
            			spaces.add(company);
            		}
            		else if(number == 38 || number == 4)
            		{
            			PayTax payTax = new PayTax(number);
            			
            			spaces.add(payTax);
            		}
            		else if(number == 30)
            		{
            			GoToPrison goToJail = new GoToPrison(number);
            			
            			spaces.add(goToJail);
            		}
            		else if (text.length() > 5) {
	            		Neighbourhood neighbour = new Neighbourhood(strArray[1], strArray[2], 
	            												   strArray[3], strArray[4], strArray[5], strArray[6], strArray[7], strArray[0]);
	            		spaces.add(neighbour);
            		}
            		else
            		{
            			FreeStop freeStop = new FreeStop( Integer.parseInt(text) );
            			spaces.add(freeStop);
            		}
            	}
            }
        } 
        catch (FileNotFoundException e) 
        {
            e.printStackTrace();
        } 
        catch (IOException e) 
        {
            e.printStackTrace();
        } 
        finally 
        {
            try 
            {
                if (reader != null) {
                    reader.close();
                }
            } 
            catch (IOException e) 
            {
                e.printStackTrace();
            }
        }
	}
	

	public Vector3 getPositionFromSpace(int spaceNumber)
	{
		float spaceStep = (spaceNumber % 10) * (borderLimit * 2.0f) / 10.0f;
		
		if 		(spaceNumber == 0) return new Vector3(5-9.0f, 5-0.8f, 0f);
		else if (spaceNumber == 1) return new Vector3(5-8.0f, 5-0.8f, 0f);
		else if (spaceNumber == 2) return new Vector3(5-7.3f, 5-0.8f, 0f);
		else if (spaceNumber == 3) return new Vector3(5-6.5f, 5-0.8f, 0f);
		else if (spaceNumber == 4) return new Vector3(5-5.7f, 5-0.8f, 0f);
		else if (spaceNumber == 5) return new Vector3(5-4.9f, 5-0.8f, 0f);
		else if (spaceNumber == 6) return new Vector3(5-4.1f, 5-0.8f, 0f);
		else if (spaceNumber == 7) return new Vector3(5-3.3f, 5-0.8f, 0f);
		else if (spaceNumber == 8) return new Vector3(5-2.5f, 5-0.8f, 0f);
		else if (spaceNumber == 9) return new Vector3(5-1.7f, 5-0.8f, 0f);
		else if (spaceNumber == 10) return new Vector3(5-0.8f, 5-0.8f, 0f);
		
		else if (spaceNumber == 11) return new Vector3(5-0.8f, 5-1.8f, 0f);
		else if (spaceNumber == 12) return new Vector3(5-0.8f, 5-2.5f, 0f);
		else if (spaceNumber == 13) return new Vector3(5-0.8f, 5-3.3f, 0f);
		else if (spaceNumber == 14) return new Vector3(5-0.8f, 5-4.1f, 0f);
		else if (spaceNumber == 15) return new Vector3(5-0.8f, 5-4.9f, 0f);
		else if (spaceNumber == 16) return new Vector3(5-0.8f, 5-5.8f, 0f);
		else if (spaceNumber == 17) return new Vector3(5-0.8f, 5-6.5f, 0f);
		else if (spaceNumber == 18) return new Vector3(5-0.8f, 5-7.3f, 0f);
		else if (spaceNumber == 19) return new Vector3(5-0.8f, 5-8.1f, 0f);
		
		else if (spaceNumber == 20) return new Vector3(5-0.8f, 5-9.0f, 0f);
		else if (spaceNumber == 21) return new Vector3(5-1.7f, 5-9.0f, 0f);
		else if (spaceNumber == 22) return new Vector3(5-2.5f, 5-9.0f, 0f);
		else if (spaceNumber == 23) return new Vector3(5-3.3f, 5-9.0f, 0f);
		else if (spaceNumber == 24) return new Vector3(5-4.1f, 5-9.0f, 0f);
		else if (spaceNumber == 25) return new Vector3(5-4.9f, 5-9.0f, 0f);
		else if (spaceNumber == 26) return new Vector3(5-5.7f, 5-9.0f, 0f);
		else if (spaceNumber == 27) return new Vector3(5-6.5f, 5-9.0f, 0f);
		else if (spaceNumber == 28) return new Vector3(5-7.3f, 5-9.0f, 0f);
		else if (spaceNumber == 29) return new Vector3(5-8.1f, 5-9.0f, 0f);
		else if (spaceNumber == 30) return new Vector3(5-9.0f, 5-9.0f, 0f);

		else if (spaceNumber == 31) return new Vector3(5-9.0f, 5-8.1f, 0f);	
		else if (spaceNumber == 32) return new Vector3(5-9.0f, 5-7.3f, 0f);
		else if (spaceNumber == 33) return new Vector3(5-9.0f, 5-6.5f, 0f);
		else if (spaceNumber == 34) return new Vector3(5-9.0f, 5-5.7f, 0f);
		else if (spaceNumber == 35) return new Vector3(5-9.0f, 5-4.9f, 0f);
		else if (spaceNumber == 36) return new Vector3(5-9.0f, 5-4.1f, 0f);
		else if (spaceNumber == 37) return new Vector3(5-9.0f, 5-3.3f, 0f);
		else if (spaceNumber == 38) return new Vector3(5-9.0f, 5-2.5f, 0f);
		else if (spaceNumber == 39) return new Vector3(5-9.0f, 5-1.8f, 0f);
		
		return new Vector3();

//		if (spaceNumber == 0)
//		{
//			return new Vector3(-borderLimit, borderLimit, 0);
//		}
//		else if	(spaceNumber == 10)
//		{
//			return new Vector3(borderLimit, borderLimit, 0);
//		}
//		else if (spaceNumber == 20)
//		{
//			return new Vector3(borderLimit, -borderLimit, 0);
//		}
//		else if (spaceNumber == 30)
//		{
//			return new Vector3(-borderLimit, -borderLimit, 0);
//		}
//		
//		else if (spaceNumber < 10)
//		{
//			return new Vector3(-borderLimit + spaceStep, borderLimit, 0);
//		}
//		else if (spaceNumber < 20)
//		{
//			return new Vector3(borderLimit, borderLimit - spaceStep, 0);
//		}
//		else if (spaceNumber < 30)
//		{
//			return new Vector3(borderLimit - spaceStep, -borderLimit, 0);
//		}
//		else if (spaceNumber < 40)
//		{
//			return new Vector3(-borderLimit, -borderLimit + spaceStep, 0);
//		}
//		return new Vector3(0, 0, 0);
	}

}
		
		// Initialize neighborhood spaces
		
