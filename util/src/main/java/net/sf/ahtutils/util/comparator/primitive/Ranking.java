package net.sf.ahtutils.util.comparator.primitive;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.google.common.primitives.Ints;

public class Ranking
{
	public Ranking()
	{
		
	}
	
	public int[] rank(int[] points)
	{
		List<Integer> tmp = rank(Ints.asList(points));
		int[] result = new int[tmp.size()];
		for(int i=0;i<tmp.size();i++){result[i]=tmp.get(i);}
		return result;
	}
	
	public List<Integer> rank(List<Integer> listPoints)
	{
		List <Rank> rankList = new ArrayList<Rank>();
		
		Rank[] sortList = new Rank[listPoints.size()]; 
		List <Integer> result = new ArrayList<Integer>();
		
		int zaehler = 0; 
		
		for (Integer i: listPoints)
		{
			rankList.add(new Rank(zaehler, i));
			zaehler++;
		}
		
		Collections.sort(rankList, new Rank());	
		
		return result;
	}
	
	int getNumberOfEqualPoints (Rank[] rankList)
	{
		int score=0, anzahl=0;
		
		for (int i=0; i<= rankList.length; i++)
		{
			score = rankList[i].getScore();
			
			for (int j=0; j<=rankList.length;j++)
			{
				if(rankList[j].getScore()==score)
				{
					anzahl++;
				}
			}
		}
		return anzahl;
	}
	
	public Rank[] setRank(Rank[] rankList)
	{
		int rankZaehler = 0, anz = 0;
		
		for (int i=rankZaehler; i<=rankZaehler+anz;)
		{
			anz = getNumberOfEqualPoints(rankList);
			rankList[i].index = rankZaehler;
			rankZaehler = rankZaehler+anz;
		}
		
		return rankList;
	}
		
	
	public class Rank implements Comparator<Rank>
	{
		public int index, score;

		public	Rank () {}
		public	Rank (int index, int score)
		{
			this.index = index;
			this.score = score;
		}

		public int getIndex() {return index;}
		public void setIndex(int index) {this.index = index;}

		public int getScore() {return score;}
		public void setScore(int score) {this.score = score;}
		@Override
		public int compare(Rank a, Rank b)
		{
			return(new Integer(a.getScore())).compareTo(b.getScore())*-1;
		}
	}
}
