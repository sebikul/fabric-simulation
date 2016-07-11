graphics_toolkit('gnuplot')
data= csvread ('../simulations/Kinetic__W=20_H=20_STEP=1.00000e-05_WSTEP=0.0166666_GRAV=true_DAMPING=true.csv');

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