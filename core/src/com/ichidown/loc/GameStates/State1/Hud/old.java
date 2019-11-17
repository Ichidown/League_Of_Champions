

/** public void DebugHudDraw(Graphics2D g)
 {
 XY=LvlState.getMouse_pos();

 //--mouse position in screen--
 //g.drawString(String.valueOf(XY.x)+" - "+String.valueOf(XY.y),850,650);
 //-------------------

 //max number of mapObjects existing at a time ......................................................

 /**newI=LvlState.GetDummies().size()
 +LvlState.GetEnemies().size()
 +LvlState.GetUnits().size();*/

/**
 for(int x=0;x<LvlState.GetUnits().size();x++)
 {
 //newI=LvlState.GetUnits().get(0).CollidedProjectilesAnimations.size();
 newI+=LvlState.GetUnits().get(x).Projectiles.size();
 }

 if(i<newI)
 {
 i = newI;
 }
 g.drawString(String.valueOf(newI),750,540);
 newI=0;
 g.drawString(String.valueOf(i),750,550);

 //..................................................................................................



 //nbr of jumps done
 //g.drawString(String.valueOf(player.getJumps()),750,650);

 //Draw colision blocs
 Enemies = LvlState.GetEnemies();
 Players = LvlState.GetUnits();



 //draw enemy Colision blocs + Length
 for(int i = 0;i<Enemies.size();i++)
 {

 //CBoxes
 if(player.getTeam()==Enemies.get(i).getTeam())
 g.setColor(Color.blue);
 else
 g.setColor(Color.red);


 g.drawRect((int) (Enemies.get(i).getx()+Enemies.get(i).xmap-Enemies.get(i).getCWidth()/2),
 (int) (Enemies.get(i).gety()+Enemies.get(i).ymap-Enemies.get(i).getCHeight()/2),
 Enemies.get(i).getCWidth(),
 Enemies.get(i).getCHeight());

 //lines from player
 /*g.drawLine( (int)(Champions.get(0).getx()+Champions.get(0).xmap),
 (int)(Champions.get(0).gety()+Champions.get(0).ymap),
 (int)(Enemies.get(i).getx()+Enemies.get(i).xmap),
 (int)(Enemies.get(i).gety()+Enemies.get(i).ymap));*/

//lines from mouse / minions
                /*g.drawLine((int) (XY.getX()),
                        (int) (XY.getY()),
                        (int) (Enemies.get(i).getx() + Enemies.get(i).xmap),
                        (int) (Enemies.get(i).gety() + Enemies.get(i).ymap));*/
//}

//draw projectile Colision blocs
/**g.setColor(Color.red);
 for(int i = 0;i<Players.get(0).Projectiles.size();i++)
 {
 g.drawRect((int) (Players.get(0).Projectiles.get(i).getx()+Players.get(0).Projectiles.get(i).xmap-Players.get(0).Projectiles.get(i).getCWidth()/2),
 (int) (Players.get(0).Projectiles.get(i).gety()+Players.get(0).Projectiles.get(i).ymap-Players.get(0).Projectiles.get(i).getCHeight()/2),
 Players.get(0).Projectiles.get(i).getCWidth(),
 Players.get(0).Projectiles.get(i).getCHeight());

 }*/
/**
 //draw player pointerPosition length
 g.setColor(Color.green);
 g.drawLine((int) (XY.getX()),
 (int) (XY.getY()),
 (int) (player.getx() + player.xmap),
 (int) (player.gety() + player.ymap));

 //player pointerPosition length value
 double xdiff = (XY.getX()-player.getx()-player.xmap);
 double ydiff = (XY.getY()-player.gety()-player.ymap);
 double tempLength = Math.sqrt(xdiff*xdiff+ydiff*ydiff);
 g.drawString(String.valueOf((int)tempLength),820,580);

 //Mark selected

 if(player.getSelectedTarget()!=null)
 {

 g.setColor(Color.yellow);
 g.drawRect((int) (player.getSelectedTarget().getx() + player.getSelectedTarget().xmap - player.getSelectedTarget().getCWidth() / 2),
 (int) (player.getSelectedTarget().gety() + player.getSelectedTarget().ymap - player.getSelectedTarget().getCHeight() / 2),
 player.getSelectedTarget().getCWidth(),
 player.getSelectedTarget().getCHeight());

 /*g.drawLine((int) (XY.getX()),
 (int) (XY.getY()),
 (int) (SelectedEnemy.getx() + SelectedEnemy.xmap),
 (int) (SelectedEnemy.gety() + SelectedEnemy.ymap));*/
/**
 }

 if(player.getLockedTarget()!=null)
 {
 g.setColor(Color.green);
 g.drawRect((int) (player.getLockedTarget().getx() + player.getLockedTarget().xmap - player.getLockedTarget().getCWidth() / 2),
 (int) (player.getLockedTarget().gety() + player.getLockedTarget().ymap - player.getLockedTarget().getCHeight() / 2),
 player.getLockedTarget().getCWidth(),
 player.getLockedTarget().getCHeight());
 }

 /*//**map mouse position

 g.setColor(Color.yellow);
 g.drawString(
 (int) (XY.getX()-Players.get(0).getXmap())+ " - " +
 (int) (XY.getY()-Players.get(0).getYmap()),600,650);
 //player position
 g.setColor(Color.green);
 g.drawString(Players.get(0).getx()+"-"+Players.get(0).gety(),600,664);*/

//}


