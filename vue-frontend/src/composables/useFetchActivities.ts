import { ref, watch, toValue, Ref } from "vue";

type Supplier = {
  id: number;
  name: string;
  address: string;
  zip: string;
  city: string;
  country: string;
};

type Activity = {
  id: string;
  title: string;
  price: number;
  currency: string;
  rating: number;
  specialOffer: boolean;
  supplier: Supplier;
};

type ResponseData = {
  results: Activity[];
  next: string | null;
  total: number;
  totalPages: number;
};

export function useFetchActivities(searchQuery: Ref<string> | string, hasSpecialOffer: Ref<boolean> | boolean) {
  const activities = ref<Activity[]>([]);
  const loading = ref(false);
  const error = ref<string | null>(null);
  const page = ref(1);
  const size = 10; // Adjust as needed
  const totalPages = ref(1);
  const hasMore = ref(true);

  const loadActivities = async (reset = false) => {
    if (loading.value || (!hasMore.value && !reset)) return;
    loading.value = true;
    error.value = null;

    try {
      const query = toValue(searchQuery);
      const specialOffer = toValue(hasSpecialOffer);
      const url = new URL(`http://localhost:8080/api/activities`);
      url.searchParams.append('query', query);
      url.searchParams.append('page', page.value.toString());
      url.searchParams.append('size', size.toString());
      if (specialOffer) {
        url.searchParams.append('hasSpecialOffer', 'true');
      }
      const response = await fetch(url.toString());
      if (!response.ok) {
        throw new Error("Network response was not ok");
      }
      const data = (await response.json()) as ResponseData;

      if (reset) {
        activities.value = data.results;
      } else {
        activities.value = [...activities.value, ...data.results];
      }

      totalPages.value = data.totalPages;
      if (page.value >= totalPages.value) {
        hasMore.value = false;
      }
    } catch (err: any) {
      console.error("Error fetching activities:", err);
      error.value = err.message;
    } finally {
      loading.value = false;
    }
  };

  const loadMore = () => {
    if (!hasMore.value) return;
    page.value += 1;
    loadActivities();
  };

  watch(
    [() => toValue(searchQuery), () => toValue(hasSpecialOffer)],
    () => {
      activities.value = [];
      page.value = 1;
      hasMore.value = true;
      loadActivities(true);
    },
    { immediate: true }
  );

  return { activities, loading, error, loadMore, hasMore };
}