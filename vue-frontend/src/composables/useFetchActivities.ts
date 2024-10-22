import { ref, watch, toValue, Ref, onUnmounted } from "vue";

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

export function useFetchActivities(
  searchQuery: Ref<string> | string
) {
  const activities = ref<Activity[]>([]);
  const loading = ref(false);
  const error = ref<string | null>(null);
  const page = ref(1);
  const size = 10;
  const totalPages = ref(1);
  const hasMore = ref(true);

  let abortController: AbortController | null = null;

  const loadActivities = async (reset = false) => {
    if (!hasMore.value && !reset) return;

    if (abortController) {
      abortController.abort();
    }
    abortController = new AbortController();

    loading.value = true;
    error.value = null;

    try {
      const query = toValue(searchQuery);
      const url = new URL(`/api/activities`, window.location.origin);
      url.searchParams.append("query", query);
      url.searchParams.append("page", page.value.toString());
      url.searchParams.append("size", size.toString());
      const response = await fetch(url.toString(), {
        signal: abortController.signal,
      });
      if (!response.ok) {
        throw new Error("Network response was not ok");
      }
      const data = (await response.json()) as ResponseData;

      if (reset) {
        activities.value = data.results;
      } else {
        activities.value.push(...data.results);
      }

      totalPages.value = data.totalPages;
      hasMore.value = page.value < totalPages.value;
    } catch (err: any) {
      if (err.name !== "AbortError") {
        console.error("Error fetching activities:", err);
        error.value = err.message;
      }
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
    () => toValue(searchQuery),
    () => {
      page.value = 1;
      hasMore.value = true;
      loadActivities(true);
    },
    { immediate: true }
  );

  onUnmounted(() => {
    if (abortController) {
      abortController.abort();
    }
  });

  return { activities, loading, error, loadMore, hasMore };
}