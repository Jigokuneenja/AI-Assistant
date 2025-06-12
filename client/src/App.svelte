<script>
    import { onMount } from 'svelte';
    let tasks = [];
    let reminders = [];
    let title = "";
    let description = "";
    let dueDate = "";
    let remindAt = "";

    onMount(async () => {
        const response = await fetch('http://localhost:8080/');
        const text = await response.text();
        console.log(text); // Should log: AI Assistant Backend is Running
    });

    async function addTask() {
        if (!title.trim()) return;

        const newTask = {
            title,
            description,
            dueDate,
            remindAt,
            status: "pending"
        };

        const res = await fetch('http://localhost:8080/api/tasks', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(newTask)
        });

        const savedTask = await res.json(); // Now includes ID from backend
        tasks = [...tasks, savedTask]; 

        // Reset fields
        title = "";
        description = "";
        dueDate = "";
        remindAt = "";
    }

    function completeTask(id) {
        tasks = tasks.map(task =>
        task.id === id ? { ...task, status: "done" } : task
        );
    }

    function deleteTask(id) {
        tasks = tasks.filter(task => task.id !== id);    
    }
</script>

<main class="p-4 max-w-xl mx-auto">
  <h1 class="text-2xl font-bold mb-4">📝 Task Manager</h1>

  <div class="space-y-2 mb-4">
    <input bind:value={title} class="w-full p-2 border rounded" placeholder="Task title" />
    <input bind:value={description} class="w-full p-2 border rounded" placeholder="Description" />
    <input bind:value={dueDate} class="w-full p-2 border rounded" type="datetime-local" placeholder="Due date" />
    <input bind:value={remindAt} class="w-full p-2 border rounded" type="datetime-local" placeholder="Remind at" />
    <button on:click={addTask} class="px-4 py-2 bg-blue-600 text-white rounded hover:bg-blue-700">
      Add Task
    </button>
  </div>

  <ul class="space-y-4">
    {#each tasks as task (task.id)}
      <li class="p-3 border rounded shadow-sm">
        <div class="flex justify-between items-center">
          <h2 class="font-semibold text-lg">{task.title}</h2>
          <span class="text-sm italic text-gray-500">{task.status}</span>
        </div>
        {#if task.description}
          <p class="text-sm text-gray-700 mt-1">{task.description}</p>
        {/if}
        {#if task.dueDate}
          <p class="text-xs mt-1">Due: {task.dueDate}</p>
        {/if}
        {#if task.remindAt}
          <p class="text-xs">Reminder: {task.remindAt}</p>
        {/if}
        <div class="mt-2 flex gap-2">
          <button on:click={() => completeTask(task.id)} class="text-green-600 hover:underline">Complete</button>
          <button on:click={() => deleteTask(task.id)} class="text-red-600 hover:underline">Delete</button>
        </div>
      </li>
    {/each}
  </ul>
</main>

<style>
    :global(body) {
    margin: 0;
    background: linear-gradient(to right, #69a6e2, #000000);
    font-family: system-ui, sans-serif;
  }

  main {
    min-height: 100vh;
    padding: 2rem;
  }
</style>