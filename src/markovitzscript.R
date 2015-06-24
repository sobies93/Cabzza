markovitz<-function(){
# Map your working directory using setwd()
# Read the relevant file

r1 <- read.csv("Markowitz.csv")


mu = colMeans(r1[,-7])  ## Calculate the column means
bigsig = cov(r1[,-7]) ## Variance-co-variance matrix


m = nrow(bigsig)-1
w = diff(c(0,sort(runif(m)), 1)); ## Assigning random weights between 0-1 to "w" which will have the dimension = no. of securities between which you have to divide your investment.


rb = sum(w*mu); ## Creating matrix "rb" which stores the E(r)


sb = sum(w*bigsig*w); ## Creating matrix "sb" which stores sigma's

max = (1000);
wages = matrix( nrow = 1000, ncol = 7)

for (j in 1:1000) {
    max=rbind(max,0);
}

N = 50000  ## Number of different combinations of "w" you want to look at


## Simulating the different combinations of weights "w's" and storing the E(r) and sigma^2

for (j in 2:N) {
w = diff(c(0,sort(runif(m)), 1));
r = sum(w*mu); rb = rbind(rb,r);
s = sum(w*bigsig*w); sb = rbind(sb,s); ## Note this is sigma^2 (variance)
k = floor(sqrt(s)*100);
if(r>max[k]){
    max[k][1]=r[1];
    wages[k,1]=w[1];
    wages[k,2]=w[2];
    wages[k,3]=w[3];
    wages[k,4]=w[4];
    wages[k,5]=w[5];
    wages[k,6]=w[6];
    wages[k,7]=r;
}
}
k = 1;
while(is.na(wages[k,1])){
k=k+1;
}
ret=wages[k,];
ret=rbind(ret,ret)
k=k+1;
while(k<1000){
    if(is.na(wages[k,7])){k=k+1;next;}
    size=nrow(ret);
    if(ret[size,7]<wages[k,7]){
        ret=rbind(ret,wages[k,]);
    }
    k=k+1;
}
return (ret);
}
d = data.frame(rb, sb); ## Merge all the E(r) and sigmas in one data.frame.

d$sb = sqrt(d$sb); ## Square root the variance to get the sigmas

plot(d$sb, d$rb, ylab="E(r)", xlab="Sigma", col="blue",  xlim = c(0.5,10), ylim = c(-0.1,0.5), main = "E(r)- sigma (With risk free asset), N = 200000")

