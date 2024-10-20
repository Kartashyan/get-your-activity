<!-- Activities.vue -->
<script setup lang="ts">
import { useFetchActivities } from "../composables/useFetchActivities";
import { useWatchSearchParam } from "../composables/useWatchSearchParam";
import { useInfiniteScroll } from "../composables/useInfiniteScroll";

const { searchQuery } = useWatchSearchParam();
const { activities, loading, error, loadMore, hasMore } = useFetchActivities(searchQuery);
const { lastItemRef } = useInfiniteScroll(loading, loadMore);
</script>

<template>
  <div>
    <input
      class="search-input"
      v-model="searchQuery"
      placeholder="Search activities..."
    />
    <div class="activities__container" v-if="activities.length">
      <div
        v-for="(activity, index) in activities"
        :key="activity.id"
        class="activities__activity"
        v-bind="{
          ref: index === activities.length - 1 && hasMore ? lastItemRef : null,
        }"
      >
        <h3>{{ activity.title }}</h3>
        <p>Price: {{ activity.price }} {{ activity.currency }}</p>
        <p>Rating: {{ activity.rating }}</p>
        <p v-if="activity.specialOffer">Special Offer Available!</p>
        <!-- Other activity details -->
      </div>
    </div>
    <div v-if="loading">Loading...</div>
    <div v-if="error">{{ error }}</div>
    <p v-if="!loading && !activities.length">No activities found.</p>
  </div>
</template>

<style lang="scss">
.activities {
  &__container {
    display: flex;
    flex-wrap: wrap;
    align-items: center;
    justify-content: center;
    margin: 0 auto;
    max-width: 1200px;
    padding: 0 20px;
  }
  &__activity {
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
    width: 100%;
    max-width: 300px;
    margin: 0 20px;
    padding: 20px;
    border: 1px solid #ccc;
    border-radius: 5px;
    text-align: center;
    transition: all 0.2s ease-in-out;
    &:hover {
      border: 1px solid #000;
      box-shadow: 0 0 10px 0 rgba(0, 0, 0, 0.2);
    }
  }
}
.search-input {
  width: 100%;
  max-width: 300px;
  margin-bottom: 20px;
  padding: 10px;
  border: 1px solid #ccc;
  border-radius: 5px;
}

@media screen and (min-width: 768px) {
  .activities {
    &__container {
      width: 100%;
      padding: 0;
      row-gap: 20px;
    }
  }
}
</style>