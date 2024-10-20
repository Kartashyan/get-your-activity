<script setup lang="ts">
import { ref } from "vue";
import { useFetchActivities } from "../composables/useFetchActivities";
import { useWatchSearchParam } from "../composables/useWatchSearchParam";
import { useInfiniteScroll } from "../composables/useInfiniteScroll";

const { searchQuery } = useWatchSearchParam();
const { activities, loading, error, loadMore, hasMore } =
  useFetchActivities(searchQuery);
const { lastItemRef } = useInfiniteScroll(loading, loadMore);
</script>

<template>
  <div class="container">
    <div class="search-container">
      <input
        class="search-input"
        v-model="searchQuery"
        placeholder="Search activities..."
      />
    </div>
    <div class="activities__container" v-if="activities.length">
      <div
        v-for="(activity, index) in activities"
        :key="activity.id"
        class="activities__activity"
        v-bind="{
          ref: index === activities.length - 1 && hasMore ? lastItemRef : null,
        }"
      >
        <div class="activity-header">
          <h3>{{ activity.title }}</h3>
          <span v-if="activity.specialOffer" class="special-offer-badge"
            >Special Offer</span
          >
        </div>
        <p>Price: {{ activity.price }} {{ activity.currency }}</p>
        <p>Rating: {{ activity.rating }}</p>
        <p>Supplier: {{ activity.supplier.name }}</p>
        <p>
          Location: {{ activity.supplier.city }},
          {{ activity.supplier.country }}
        </p>
      </div>
    </div>

    <div v-if="error">{{ error }}</div>
    <p v-if="!loading && !activities.length" class="activities__container">
      No activities found.
    </p>
  </div>
</template>

<style lang="scss">
.container {
  display: flex;
  position: relative;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  min-height: 100vh;
  font-size: 20px;
}
.activities {
  &__container {
    display: flex;
    flex-wrap: wrap;
    gap: 20px;
    align-items: flex-start;
    justify-content: center;
    margin: 0 auto;
    max-width: 1200px;
    padding: 24px;
  }
  &__activity {
    display: flex;
    flex-direction: column;
    align-items: flex-start;
    width: 100%;
    max-width: 300px;
    height: 350px;
    padding: 20px;
    border: 1px solid #ccc;
    border-radius: 5px;
    text-align: left;
    transition: all 0.2s ease-in-out;
    overflow: hidden;
    position: relative;
    &:hover {
      border: 1px solid #000;
      box-shadow: 0 0 10px 0 rgba(0, 0, 0, 0.2);
    }
    h3 {
      margin-bottom: 10px;
    }
    p {
      margin: 5px 0;
    }
    .activity-header {
      position: relative;
      width: 100%;
      .special-offer-badge {
        position: absolute;
        top: 0;
        right: 0;
        background-color: #ff4081;
        color: white;
        padding: 4px 8px;
        border-radius: 5px;
        font-size: 8px;
        font-weight: bold;
      }
    }
  }
}
.search-container {
  display: flex;
  align-items: center;
  justify-content: center;
  margin-bottom: 20px;
  gap: 20px;
  position: fixed;
  top: 10px;
  width: 70%;
  background: linear-gradient(
    45deg,
    rgba(255, 0, 150, 0.5) 0%,
    rgba(255, 204, 0, 0.5) 100%
  );
  backdrop-filter: blur(3px);
  border-radius: 3px;
  padding: 10px;
  z-index: 10;
}
.search-input {
  width: 100%;
  max-width: 300px;
  padding: 10px;
  border: 1px solid #ccc;
  border-radius: 5px;
}
.special-offer-checkbox {
  display: flex;
  align-items: center;
  font-size: 16px;
  input {
    margin-right: 5px;
  }
}
.loading-inline {
  font-size: 16px;
}
.loading-more {
  text-align: center;
  margin: 20px 0;
}

@media screen and (min-width: 768px) {
  .activities {
    &__activity {
      flex: 1 1 calc(33.333% - 40px);
      margin: 10px;
    }
  }
}
</style>