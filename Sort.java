class Sort{
  /*
     The logic is to insert element from unsorted array to sorted array , not swapping it.
     Insert it at last.
  */
  private int[] insertionSort(int array[]){
    int j = 0;
    int key;
    for(int i = 0; i < array.length - 1 ; i++){
        j = i+1;
        key = array[j];
        while((j>0) && (key < array[j-1]))  {
            array[j] = array[j-1];
            j--;
        }
        array[j] = key;
    }
    return array;
  }
  private int getMaxDigit(int array[]){
      int max = array[0];
      for(int i = 1; i < array.length ; i++){
        if(array[i] > max)
            max = array[i];
      }
      int num_digits = 0;
      while(max > 0){
        max = max / 10;
        num_digits++;
      }
      return num_digits;
  }
   private void radixSort(int array[]){
     int digit = 1;
     int max_digit_numbers = getMaxDigit(array);
      while(digit <= max_digit_numbers){
     countingSortHelperForRadixSort(array, digit);
     digit++;
      }
     printArray(array);
   }
   private int linearSearch(int array[],int data){
     for(int i = 0; i <array.length ; i++){
       if(array[i] == data)
        return i;
     }
     return -1;
   }

   private int binarySearch(int array[], int start, int end, int data){
     insertionSort(array);
     int mid = start + (end - start) / 2 ;
     if ( start > end)
      return -1;
     if(data == array[mid])
      return mid;
     else if(data < array[mid])
      return binarySearch(array, start , mid , data);
     else
      return binarySearch(array, mid + 1 , end , data);
   }

   private void countingSortHelperForRadixSort(int array[], int digit){
     int[] buckets = new int[10]; // digits are 0..9
     int n = array.length;
     int[] result = new int[n];
     for(int i = 0; i < 10 ; i++)
       buckets[i] = 0;

     // Place each value into respective bucket
     for(int i = 0; i < n ; i++)
       buckets[getDigit(array[i], digit)]++;

      // Add cumulative sum.
       for(int i = 1; i < 10 ; i++){
         buckets[i] += buckets[i-1];
       }

       // Place each item in result array and decrement count.
       // Traversing array in reverse order makes it stable
       // For unstable traverse it straight.
       // See comment in geeksforgeeks counting sort video on Youtube.
       for(int i = n-1; i >= 0 ; i--){
          result[buckets[getDigit(array[i],digit)] - 1] = array[i]; // -1 is important.
          buckets[getDigit(array[i],digit)] --;
       }
       System.arraycopy(result , 0 , array ,0 , n);
   }
   private int getDigit(int number, int digit){
     int value_in_digit=0;
     while( (number > 0) && (digit > 0)){
       value_in_digit = number % 10;
       number = number / 10;
       digit--;
     }
     if(digit > 0)
      return 0;
     else
      return value_in_digit;
   }
 // 3, 100, 2, 0, 99 , 77, 33,56, 1
  private void countingSort(int array[], int storage){
    int[] buckets = new int[storage]; // 0.. to 100
    int n = array.length;
    int[] result = new int[n];
    int running_sum = 0;
    for(int i = 0; i < storage ; i++)
      buckets[i] = 0;
    // Place each value into respective bucket
    for(int i = 0; i < n ; i++)
      buckets[array[i]]++;

    // Add cumulative sum.
    for(int i = 1; i < storage ; i++){
      buckets[i] += buckets[i-1];
    }

   // Place each item in result array and decrement count.
    for(int i = 0; i < n ; i++){
       result[buckets[array[i]] - 1] = array[i]; // -1 is important.
       buckets[array[i]]--;
    }
    printArray(result);
  }

/*
 Heap sort: Build a binary min heap and delete  values till size of heap is 1.
*/
 private void heapSort(int array[]){
   int heap_size = array.length - 1;
   if(heap_size == 1)
    return;
   buildHeap(array);
   // Place maximum as last element
   swap(array, 0 , heap_size);
   heap_size--;
   for(int i = 1 ; i < array.length ; i++ ){
     percolateDown(array, 0 , heap_size); // second parameter is ending point.
     swap(array, 0, heap_size);
     heap_size--;
   }
   printArray(array);
 }
 private void buildHeap(int array[]){
   /*
      Non - leaf nodes start from n/2 - 1 in array , we just need to percolateDown from them.
      We can also insert each element into heap but it will be O(nlogn) operation.
      Hence, we start from non-leaf bottom - up.
   */
   int n = array.length - 1;
   for(int i = n/2 - 1 ; i >= 0 ; i--){
     percolateDown(array, i, n);
   }
 }

 // n is the size of heap, not array.

 private void percolateDown(int array[], int parent , int n){
   int child1  =  2 * parent + 1;
   int child2 = 2 * parent + 2;
   int child;

   if((parent > n) || (child1 > n) && (child2 > n))
    return;
   if( (child1 <= n) && (child2 > n))
      child = child1;
   else{
      if(array[child1] > array[child2])
        child = child1;
      else
        child = child2;
   }
  if(array[child] > array[parent]) {
    swap(array, parent , child);
    percolateDown(array, child , n);
  }

}


 private boolean isOdd(int number){
   if(number % 2 == 0)
    return false;
   else
      return true;
 }

/*
Selection sort logic is that we select minimum each time  and add it to sorted array.

*/
  private int[] selectionSort(int array[]){
    int min;

    for(int i = 0 ; i <array.length ; i++){
       min = i;
       for(int j = i ; j < array.length; j++){ // find min.
         if(array[j] < array[min])
          min = j;
       }
      // swap i and min element
      int temp = array[min];
      array[min] = array[i];
      array[i] = temp;
    }
    return array;
  }

/*
Merge sort:
Divide array recursively till single elements.
Compare and merge them.
*/
  private void mergeSort(int array[], int low , int high){

    if(low < high){
      int mid = low  + ((high - low ) /2 ); // Avoid buffer overflow.
      mergeSort(array, low, mid);
      mergeSort(array, mid + 1 , high);
      merge(array, low , mid , high);
    }
  }
/*
 Merging logic is compare heads of each sorted list. Add rest of them to main list.
*/
  private void merge(int array[] , int low , int mid, int high){
    int i = 0;
    int j = 0;
    int k = 0;
    int n1 = mid - low + 1;
    int n2 = high - mid;
    int leftArray[] = new int[n1];
    int rightArray[] = new int[n2];
    for (i = 0; i < n1; i++)
        leftArray[i] = array[low + i];
    for (j = 0; j < n2; j++)
        rightArray[j] = array[mid + 1+ j];
    i = 0;
    j = 0;
    k = low;
    while( (i < n1 ) && (j < n2)){
      // Compare heads and insert into main array.
      if(leftArray[i] <= rightArray[j]){
           array[k] = leftArray[i];
           i++;
      }
      else{
             array[k] = rightArray[j];
             j++;
      }
        k++;
    }
    while(i < n1){
              array[k] = leftArray[i];
              k++;
              i++;
    }
    while(j < n2){
          array[k] = rightArray[j];
          k++;
          j++;
        }

  }

  private void quickSort(int array[], int low , int high){

    if(low < high){
      int pivot_index = low;
      int index = find(array, pivot_index);
      quickSort(array, 0 , index - 1);
      quickSort(array, index + 1, high);
    }
  }

  private int find(int array[] , int pivot_index){
    int i = pivot_index;
    int pivot = array[pivot_index];
    int j = i + 1;
    while(j < array.length){
      // we go from left to right
      // i is slower and j is faster
      // if a[i] is greater than pivot we swap it with a[j] which is lower than pivot.
      if(array[i] >= pivot){
        if (array[j] < pivot){
          swap(array, i , j);
        if(i == pivot_index)
          pivot_index = j; // update pivot index.
        i++;
        }
        j++;
      }

      else{
        if (array[j] >= pivot)
            j++;
        i++;
      }
    }
   swap(array, i ,pivot_index);
    return i;
  }

  void swap(int array[] , int first ,int second){
    int temp = array[first];
    array[first] = array[second];
    array[second] = temp;
  }

  private void printArray(int array[]){
    for(int i = 0 ; i < array.length ; i++)
      System.out.print(array[i] + " ");
      System.out.println("");
  }
  public static void main(String[] args) {
    Sort sort = new Sort();
    int arr[] = {3,88,2,0,100,3894,1,9823,87};
    sort.printArray(arr);
    System.out.println(sort.binarySearch(arr , 0 , arr.length - 1, 87));
    sort.radixSort(arr);
    sort.countingSort(arr , 10000);
    sort.heapSort(arr);
    sort.quickSort(arr,0 , arr.length -1);
    sort.printArray(arr);
    arr = sort.selectionSort(arr);
    sort.printArray(arr);
    sort.mergeSort(arr , 0 , arr.length -  1);
    sort.printArray(arr);
  }
}
