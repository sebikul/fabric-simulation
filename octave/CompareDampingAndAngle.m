# script para comparar valores de amortiguamiento y angulos

#Eje x: valor de amortiguamiento
#Eje y: tiempo de relajacion promedio (entre los distintos angulos), con barra de error.


W=4;
H=4;
margenError=10^-3;
baseFileName="../simulations/Kinetic__W=%d_H=%d_STEP=1.00000e-05_WSTEP=0.100000_GRAV=true_DAMPING=exp%.1f_ANGLE=%.1f.csv";
forceAngles=0.0:22.5:90.0;
dampingExps=-2:0.5:2;
data=zeros(columns(dampingExps),3); #exponente, promedio,error

dampingIndex=1;
for dampingExp=dampingExps
  timesForExp=zeros(columns(forceAngles),1);
  angleIndex=1;
    for forceAngle=forceAngles
       currentFileName=sprintf(baseFileName,W,H,dampingExp,forceAngle);
       currentTime=getRelaxationTime(margenError,currentFileName);
       timesForExp(angleIndex)=currentTime;
       angleIndex=angleIndex+1;
    endfor
    
    avg=mean(timesForExp);
    error=std(timesForExp)/2;
    
    data(dampingIndex,:)=[10^(dampingExp),avg,error];
    
    dampingIndex=dampingIndex+1;
endfor

errorbar(data(:,1),data(:,2),data(:,3));
xlabel("Coeficiente de amortiguamiento");
ylabel("Tiempo de relajacion");

