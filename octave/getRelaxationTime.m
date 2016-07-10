#determina el tiempo en el que se relaja la tela
function  ans=getRelaxationTime(margenError,fileName)
  data= csvread (fileName);
  time=data(:,1);
  kineticEnergy=data(:,2);
  ans=0;
  for i=1:rows(kineticEnergy)
      ans=time(i);
      
      if(kineticEnergy(i)<margenError)
        break;
      endif
      
      
  endfor
  

  
endfunction