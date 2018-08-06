package twothousandfourtyeight;

import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class Grid {
	boolean hasMoved = false; //if there is no movement in the grid, no spawns will occur. This mechanism needs improvement
	Block[][] grid;
	Block[][] temp; //to compare before and after move to see if there was a move at all
	private Game game;
	boolean noObstacles = true; //checks to see if there are obstacles in the way of the block to be combined with another block
	ArrayList<Coordinates> emptySpaces;
	int[] choices = {2, 4, 8}; //possible spawn blocks
	public Grid(Game game) //make randomizer for inserting squares into grid locations
	{
    	grid = new Block[4][4]; 
		grid[0][0] = new Block(4);
		grid[2][0] = new Block(4);
		grid[3][0] = new Block(8);
	/*	grid[2][0] = new Block(2); 
		grid[3][0] = new Block(32);
		grid[0][1] = new Block(2);
		grid[1][1] = new Block(128); 
		grid[2][1] = new Block(32); 
		grid[3][1] = new Block(16); 
		grid[0][2] = new Block(32);
		grid[1][2] = new Block(8); 
		grid[2][2] = new Block(2); 
		grid[3][2] = new Block(2); 
		grid[0][3] = new Block(4);
		grid[1][3] = new Block(2); 
		grid[2][3] = new Block(16); 
		grid[3][3] = new Block(8); */
	/*	for(int y = 0; y < grid.length; y++)
		{
			for(int x = 0; x < 1; x++)
			{
				grid[x][y] = new Block(2);
			}
			for(int x = 2; x < 3; x++)
			{
				grid[x][y] = new Block(2);
			}
			for(int x = 3; x < 4; x++)
			{
				grid[x][y] = new Block(4);
			}
			for(int x = 1; x < 2; x++)
			{
				grid[x][y] = new Block(4);
			}
		} */
		this.game = game;
	}
	public void keyPressed(KeyEvent e)
	{
		temp = new Block[4][4];
		for(int x = 0; x < grid.length; x++)
		{
			for(int y = 0; y < grid[0].length; y++)
			{
				if(grid[x][y] != null)
				{
					temp[x][y] = grid[x][y];
				}
			}
		}
		if(checkDefeat() == false)
		{
			game.gameOver();
		}
		if (e.getKeyCode() == KeyEvent.VK_LEFT)
		{
			for(int x = 0; x < grid.length; x++) //combining blocks with some distance apart
			{
				for(int y = 0; y < grid[0].length; y++)
				{
					if(grid[x][y] != null)
					{
						for(int i = x + 1; i < grid[0].length; i++) //+1 because the left most column will not move no matter what
						{											//the purpose of this for loop is to find a matching block in the row
							noObstacles = true; //resets every time it checks a new block
							if(grid[i][y] != null)
							{
								if(grid[i][y].value == grid[x][y].value && (grid[i][y].alreadyAdded != true || grid[x][y].alreadyAdded != true)) //if the blocks are equal and haven't been added already(prevents double adding)
								{
									for(int j = i; j >= x; j--) //this goes backward from the picked block to see if there are any obstacles in the way
									{
										if(grid[j][y] != null && grid[j][y].value != grid[x][y].value)
										{
											noObstacles = false;
										}
									}
									if(noObstacles && grid[x][y].alreadyAdded == false) //Update: added alreadyAdded here to prevent double adds again
									{
										int added = grid[x][y].value + grid[i][y].value;
										grid[x][y].value = added;
										grid[x][y].alreadyAdded = true;
										grid[i][y] = null; //previous occupied space of picked block is set to null
									}
								}
							}
						}
					}
				}
			}
			for(int x = 1; x < grid.length; x++) //pushing blocks left through empty space
			{									 //+1 because the left border is irrelevant when it comes to movement through empty space when moving left
				for(int y = 0; y < grid[0].length; y++)
				{
					if(grid[x][y] != null && grid[x-1][y] == null) //if the block to the left is null, it will check the entire row
					{
						for(int i = x; i >= 0; i--) //goes to the left most null spot
						{
							if(i == 0 && grid[i][y] == null) //if it reaches 0 and the 0 spot is null, it moves the block to the left border
							{
								grid[i][y] = grid[x][y];
								grid[x][y] = null;
							}
							if(i != 0 && grid[i][y] == null && grid[i-1][y] != null) //if it has found a space that is not null, then it moves the block to the space one space right to the filled space where there is a null space
							{
								grid[i][y] = grid[x][y];
								grid[x][y] = null;
							}
						} 
					}
				}
			}
		} //EVERYTHING BELOW FOLLOWS A SIMILAR LOGIC TO ABOVE JUST WITH DIFFERENT BORDERS
		else if (e.getKeyCode() == KeyEvent.VK_UP)
		{
			for(int x = 0; x < grid.length; x++) //combining blocks with some distance apart
			{
				for(int y = 0; y < grid[0].length; y++)
				{
					if(grid[x][y] != null)
					{
						for(int i = y + 1; i < grid[0].length; i++)
						{
							noObstacles = true;
							if(grid[x][i] != null)
							{
								if(grid[x][y].value == grid[x][i].value && (grid[x][y].alreadyAdded != true && grid[x][i].alreadyAdded != true))
								{
									for(int j = i; j > y; j--)
									{
										if(grid[x][j] != null && grid[x][j].value != grid[x][y].value)
										{
											noObstacles = false;
										}
									}
									if(noObstacles && grid[x][y].alreadyAdded == false)
									{
										int added = grid[x][y].value + grid[x][i].value;
										grid[x][y].value = added;
										grid[x][i] = null;
										grid[x][y].alreadyAdded = true;
									}
								}
							}
						}
					}
				}
			}
			for(int x = 0; x < grid.length; x++) //pushing blocks up empty space
			{
				for(int y = 1; y < grid[0].length; y++)
				{
					if(grid[x][y] != null && grid[x][y-1] == null)
					{
						for(int i = y; i >= 0; i--)
						{
							if(i == 0 && grid[x][i] == null)
							{
								grid[x][i] = grid[x][y];
								grid[x][y] = null;
							}
							if(i != 0 && grid[x][i] == null && grid[x][i-1] != null)
							{
								grid[x][i] = grid[x][y];
								grid[x][y] = null;
							}
						} 
					}
				}
			}
		}
		else if(e.getKeyCode() == KeyEvent.VK_RIGHT)
		{
			for(int x = grid.length - 1; x >= 0; x--) //combining blocks with some distance apart
			{
				for(int y = grid[0].length - 1; y >= 0; y--)
				{
					if(grid[x][y] != null)
					{
						for(int i = x - 1; i >= 0; i--)
						{
							noObstacles = true;
							if(grid[i][y] != null)
							{
								if(grid[i][y].value == grid[x][y].value && (grid[i][y].alreadyAdded != true && grid[x][y].alreadyAdded != true))
								{
									for(int j = i; j < x; j++)
									{
										if(grid[j][y] != null && grid[j][y].value != grid[x][y].value)
										{
											noObstacles = false;
										}
									}
									if(noObstacles && grid[x][y].alreadyAdded == false)
									{
										int added = grid[i][y].value + grid[x][y].value;
										grid[x][y].value = added;
										grid[x][y].alreadyAdded = true;
										grid[i][y] = null;
									}
								}
							}
						}
					}
				}
			}
			for(int x = grid.length - 2; x >= 0; x--) //pushing blocks right through empty space
			{
				for(int y = grid[0].length - 1; y >= 0; y--)
				{
					if(grid[x][y] != null && grid[x+1][y] == null)
					{
						for(int i = x; i < grid.length; i++)
						{
							if(i == grid.length - 1 && grid[i][y] == null)
							{
								grid[i][y] = grid[x][y];
								grid[x][y] = null;
							}
							if(i != grid.length - 1 && grid[i][y] == null && grid[i+1][y] != null)
							{
								grid[i][y] = grid[x][y];
								grid[x][y] = null;
							}
						} 
					}
				}
			}
		}
		else if(e.getKeyCode() == KeyEvent.VK_DOWN)
		{
			for(int x = grid.length - 1; x >= 0; x--) //combining blocks with some distance apart
			{
				for(int y = grid[0].length - 1; y >= 0; y--)
				{
					if(grid[x][y] != null)
					{
						for(int i = y - 1; i >= 0; i--)
						{
							noObstacles = true;
							if(grid[x][i] != null)
							{
								if(grid[x][i].value == grid[x][y].value && (grid[x][i].alreadyAdded != true && grid[x][y].alreadyAdded != true))
								{
									for(int j = i; j < y; j++)
									{
										if(grid[x][j] != null && grid[x][j].value != grid[x][y].value)
										{
											noObstacles = false;
										}
									}
									if(noObstacles && grid[x][y].alreadyAdded == false)
									{
										int added = grid[x][i].value + grid[x][y].value;
										grid[x][y].value = added;
										grid[x][y].alreadyAdded = true;
										grid[x][i] = null;
									}
								}
							}
						}
					}
				}
			}
			for(int x = grid.length - 1; x >= 0; x--) //pushing blocks down empty space
			{
				for(int y = grid[0].length - 2; y >= 0; y--)
				{
					if(grid[x][y] != null && grid[x][y+1] == null)
					{
						for(int i = y; i < grid[0].length; i++)
						{
							if(i == grid[0].length - 1 && grid[x][i] == null)
							{
								grid[x][i] = grid[x][y];
								grid[x][y] = null;
							}
							if(i != grid[0].length - 1 && grid[x][i] == null && grid[x][i+1] != null)
							{
								grid[x][i] = grid[x][y];
								grid[x][y] = null;
							}
						} 
					}
				}
			}
		}
		for(int x = 0; x < grid.length; x++) //resetting whether it was already added or not
		{
			for(int y = 0; y < grid[0].length; y++)
			{
				if(grid[x][y] != null)
				{
					grid[x][y].alreadyAdded = false;
				}
				if((grid[x][y] == null && temp[x][y] != null) || (grid[x][y] != null && temp[x][y] == null) || (temp[x][y] != null & grid[x][y] != null && grid[x][y].value != temp[x][y].value))
				{
					hasMoved = true;
				}
			}
		}
		int coin = (int)(Math.random() * 10); //a randomizer to determine whether there is a spawn or not of a new block
		if(blockCount() <= 4) //less than 4 blocks on grid will always spawn something
		{
			coin = 3;
		}
		if((coin == 3 || coin == 5 || coin == 7) && blockCount() != 16 && hasMoved)
		{
			spawn();
		}
		noObstacles = true;
		hasMoved = false;
	}
	public void spawn()
	{
		emptySpaces = new ArrayList<Coordinates>();
		for(int x = 0; x < grid.length; x++)
		{
			for(int y = 0; y < grid[0].length; y++)
			{
				if(grid[x][y] == null)
				{
					emptySpaces.add(new Coordinates(x, y));
				}
			}
		}
		int pick = (int)(Math.random() * emptySpaces.size());
		int choice = (int)(Math.random() * choices.length);
		grid[emptySpaces.get(pick).x][emptySpaces.get(pick).y] = new Block(choices[choice]);
	}
	public int blockCount()
	{
		int blockCount = 0;
		for(int x = 0; x < grid.length; x++)
		{
			for(int y = 0; y < grid.length; y++)
			{
				if(grid[x][y] != null)
				{
					if(grid[x][y].value == 2048)
					{
						game.victory();
					}
					blockCount++;
				}
			}
		}
		return blockCount;
	}
	public boolean checkDefeat() //checks the grid to see if there are any possible moves or checks the grid to see if 2048 is reached. Make boolean to signal victory or defeat
	{
		if(blockCount() != 16)
		{
			return true;
		}
		else if(blockCount() == 16)
		{
			for(int x = 0; x < grid.length; x++)
			{
				for(int y = 0; y < grid[0].length; y++)
				{
					if(grid[x][y] != null)
					{
						if(checkSurrounding(x, y))
						{
							return true;
						}
					}
				}
			}
		}
		return false;
	}
	public boolean checkSurrounding(int x, int y) //need to code in sides
	{
		//TESTING ALL ADJACENT BLOCKS
		if(x != 0 && x != (grid.length-1) && y != 0 && y != (grid.length-1)) //center four blocks
		{
			if((grid[x-1][y] != null && grid[x-1][y].value == grid[x][y].value) || (grid[x+1][y] != null && grid[x+1][y].value == grid[x][y].value) || (grid[x][y-1] != null && grid[x][y-1].value == grid[x][y].value) || (grid[x][y+1] != null && grid[x][y+1].value == grid[x][y].value))
			{
				return true;
			}
		}
		if(x == 0 && y == 0)
		{
			if((grid[x+1][y] != null && grid[x+1][y].value == grid[x][y].value) || (grid[x][y+1] != null && grid[x][y+1].value == grid[x][y].value))
			{
				return true;
			}
		}
		if(x == 0 && y == 3)
		{
			if((grid[x+1][y] != null && grid[x+1][y].value == grid[x][y].value) || (grid[x][y-1] != null && grid[x][y-1].value == grid[x][y].value))
			{
				return true;
			}
		}
		if(x == 3 && y == 0)
		{
			if((grid[x-1][y] != null && grid[x-1][y].value == grid[x][y].value) || (grid[x][y+1] != null && grid[x][y+1].value == grid[x][y].value))
			{
				return true;
			}
		}
		if(x == 3 && y == 3)
		{
			if((grid[x-1][y] != null && grid[x-1][y].value == grid[x][y].value) || (grid[x][y-1] != null && grid[x][y-1].value == grid[x][y].value))
			{
				return true;
			}
		}
		if(x == 0 && y != 0 && y != 3)
		{
			if(((grid[x+1][y] != null && grid[x+1][y].value == grid[x][y].value) || (grid[x][y-1] != null && grid[x][y-1].value == grid[x][y].value) || (grid[x][y+1] != null && grid[x][y+1].value == grid[x][y].value)))
			{
				return true;
			}
		}
		if(x == 3 && y != 0 && y != 3)
		{
			if((grid[x-1][y] != null && grid[x-1][y].value == grid[x][y].value) || (grid[x][y-1] != null && grid[x][y-1].value == grid[x][y].value) || (grid[x][y+1] != null && grid[x][y+1].value == grid[x][y].value))
			{
				return true;
			}
		}
		if(y == 0 && x != 0 && x != 3)
		{
			if((grid[x-1][y] != null && grid[x-1][y].value == grid[x][y].value) || (grid[x+1][y] != null && grid[x+1][y].value == grid[x][y].value) || (grid[x][y+1] != null && grid[x][y+1].value == grid[x][y].value))
			{
				return true;
			}
		}
		if(y == 3 && x != 0 && x != 3)
		{
			if((grid[x-1][y] != null && grid[x-1][y].value == grid[x][y].value) || (grid[x+1][y] != null && grid[x+1][y].value == grid[x][y].value) || (grid[x][y-1] != null && grid[x][y-1].value == grid[x][y].value))
			{
				return true;
			}
		}
		return false;
	}
}
