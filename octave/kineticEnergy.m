graphics_toolkit('gnuplot')
data= csvread ('../simulations/Kinetic__W=4_H=4_STEP=1.00000e-05_WSTEP=0.100000_GRAV=true_DAMPING=exp-1.0_ANGLE=0.0.csv');

time=data(:,1);
kinetic=data(:,2);
potentialGravity=data(:,3);
potentialSpring=data(:,4);
totalEnergy=data(:,5);

#plot(time,kinetic,time,potentialGravity,time,potentialSpring,time,totalEnergy);
#legend('Ek prom','Epot gravitatoria prom', 'Epot elastica prom','Etotal prom');
#plot(time,kinetic,time,potentialSpring);
#legend('Ek prom','Epot elastica prom');


plot(time,kinetic,time,potentialSpring);
legend('Ek prom', 'Epot elastica prom');

#plot(time,totalEnergy);
#legend('Etot');


xlabel('Tiempo');
ylabel('Energia[J]');