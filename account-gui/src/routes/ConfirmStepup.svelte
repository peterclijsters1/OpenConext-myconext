<script>
    import I18n from "i18n-js";
    import {onMount} from 'svelte';
    import {conf, links} from "../stores/conf";
    import Button from "../components/Button.svelte";
    import Verification from "../components/Verification.svelte";
    import {fetchServiceNameByHash} from "../api";
    import Spinner from "../components/Spinner.svelte";
    import {proceed} from "../utils/sso";

    let serviceName = null;
    let explanation = null;
    let showSpinner = true;

    onMount(() => {
        $links.displayBackArrow = false;

        const urlSearchParams = new URLSearchParams(window.location.search);
        explanation = decodeURIComponent(urlSearchParams.get("explanation"));
        const hash = urlSearchParams.get('h')
        fetchServiceNameByHash(hash).then(res => {
            serviceName = res.name;
            showSpinner = false;
        });

    });

</script>

<style>

</style>
{#if showSpinner}
    <Spinner/>
{/if}
<h2 class="green">{I18n.t("confirmStepup.header")}</h2>
<Verification explanation={explanation} verified={true}/>
<Button href="/proceed" onClick={() => proceed($conf.magicLinkUrl)}
        className="full"
        label={I18n.t("confirmStepup.proceed", {name: serviceName})}/>
