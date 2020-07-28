<template>
    <svg class="icon svg-icon" aria-hidden="true">
        <use :xlink:href="`#i-${name}`"></use>
    </svg>
</template>

<script>
    export default {
        name: "IconFont",
        props: ['name']

    }
</script>

<style scoped>

</style>
