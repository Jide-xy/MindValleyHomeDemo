# MindValley Test

### What parts of the test did you find challenging and why?

- Restoring the position of the nested `RecyclerView` proved challenging as the new `ConcatAdapter` returned `NO_POSITION` in `onViewRecycled`. I solved this by caching the last position of the `ViewHolder` in `onBindViewHolder`
- I found writing the UI test challenging because `Hilt` is relatively new and resources to solve problems faced were limited. However, I was able to complete the setup successfully. But, due to time constraints, I was unable to write proper UI test after the setup.

### What feature would you like to add in the future to improve the project?

- I would use a `ListAdapter` to optimize the performance of the `RecyclerViews`
- I would create error `ViewHolders` with retry buttons for each section to allow refreshing each section independently