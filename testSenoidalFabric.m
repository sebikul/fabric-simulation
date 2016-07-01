WIDTH= 10
HEIGHT =5
PARTICLE_SEPARATION= 3;
INITIAL_Z=0;

particleArray= zeros(HEIGHT *WIDTH,3);
index=1;


vertical_lines=WIDTH;
totaL_width=(vertical_lines-1)*PARTICLE_SEPARATION;
z_values=sin((0:PARTICLE_SEPARATION:totaL_width)./4)

for i=1:HEIGHT
  
  for j=1:WIDTH
  zpos=z_values(j);
  xpos=j*PARTICLE_SEPARATION;
  ypos=i*PARTICLE_SEPARATION;
  
  
 
  
  particleArray(index,1)=xpos;
  particleArray(index,2)=ypos;
  particleArray(index,3)=zpos;
  index=index+1;
  
  endfor
endfor 

scatter3(particleArray(:,1),particleArray(:,2),particleArray(:,3));
xlabel("x");
ylabel("y");
zlabel("z");