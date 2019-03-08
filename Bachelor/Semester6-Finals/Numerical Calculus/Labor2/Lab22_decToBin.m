x=520
z=0;
i=0;
while(x>0)  
  y = mod(x,2);
  z = z + y * 10.^i;
  x = floor(x/2);
  i = i + 1;
endwhile
z
x
%i=i-1
y = z;
while(i>=0)
  2.^i
  x = 2.^i+x;
  i = i - 1;
endwhile
x