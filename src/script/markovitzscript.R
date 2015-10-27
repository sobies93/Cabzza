# markowitzTest <-function(param,mode){
# 	r1 <- read.csv("Markowitz.csv")
# 	markowitz(r1,param,mode)
# }
# r1 is matrix of stock prices, param is number form 1-100 set by user, mode is chosen mode (optimal, given risk, given return)

markowitz<-function(r1,param,mode){

	n = ncol(r1)
	mu = colMeans(r1[,-n])  ## Calculate the column means 
	bigsig = cov(r1[,-n]) ## Variance-co-variance matrix


	m = nrow(bigsig)-1
	w = diff(c(0,sort(runif(m)), 1)); ## Assigning random weights between 0-1 to "w" which will have the dimension = no. of securities between which you have to divide your investment.


	rb = sum(w*mu); ## Creating matrix "rb" which stores the E(r)

	sb = sum(w*bigsig*w); ## Creating matrix "sb" which stores sigma's

	max = (1000);
	min = (1000);

	wages = matrix( nrow = 1000, ncol = n)
	
	wagesMax = matrix( nrow = 1000, ncol = n)
	wagesMin = matrix( nrow = 1000, ncol = n)

	for (j in 1:1000) {
		max=rbind(max,0);
		min=rbind(min,1000);
	}

	N = 20000  ## Number of different combinations of "w" you want to look at


	## Simulating the different combinations of weights "w's" and storing the E(r) and sigma^2
	optswartz=-100;
	optr=0;
	opts=0;

	for (j in 1:N) {
		w = diff(c(0,sort(runif(m)), 1));
		r = sum(w*mu); 
		rb = rbind(rb,r); 
		s = sum(w*bigsig*w); 
		sb = rbind(sb,s); ## Note this is sigma^2 (variance)
		k = floor(sqrt(s)*100);
		if(r>max[k]){
			max[k]=r;
			for(m in 1:n){
				wagesMax[k,m]=w[m];
			}
		}
		rIndex = floor (r*1000) ;
		#return (rIndex)
		if(k<min[rIndex]){
			min[rIndex]=k;
			for(m in 1:n){
				wagesMin[rIndex,m]=w[m];
			}
		}
		if((r/k)>optswartz){
			optswartz =r/s;
			optr =r;
			opts =s;
			schwartzWages = w;
		}
	}
	k = 1;
	while(is.na(wagesMax[k,1])){
		k=k+1;
	}
	minK = k;
	while(k<1000){
		if(is.na(wagesMax[k,1])){k=k+1;next;}
		maxK = k;
		k=k+1;
	}
	r = 1;
	while(is.na(wagesMin[r,1])){
		r=r+1;
	}
	minR = r;
	while(r<1000){
		if(is.na(wagesMin[r,1])){r=r+1;next;}
		maxR= r;
		r=r+1;
	}
	if(mode == 0){ 
		ret = rbind(schwartzWages,optr);
		ret = rbind(ret,opts);
		return (ret);
	}
	if(mode == 1){
		# param beetwen 1 to 100
		ret = rbind(wagesMax [floor((maxK-minK)*param/100)+minK,],max[floor((maxK-minK)*param/100)])
		return (ret)
	}
	if(mode == 2){
		# param beetwen 1 to 100
		ret = rbind(wagesMin [floor((maxR-minR)*param/100)+minR,],min[floor((maxR-minR)*param/100)])
		return (ret)
	}
}
