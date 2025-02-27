package com.bencodez.votingplugin;

import java.util.concurrent.Callable;

import com.bencodez.advancedcore.api.metrics.BStatsMetrics;
import com.bencodez.advancedcore.api.rewards.RewardHandler;
import com.bencodez.votingplugin.user.UserManager;

public class VotingPluginMetrics {

	public void load(VotingPluginMain plugin) {
		BStatsMetrics metrics = new BStatsMetrics(plugin, 38);

		metrics.addCustomChart(new BStatsMetrics.SimplePie("extrarewards_firstvote", new Callable<String>() {

			@Override
			public String call() throws Exception {
				if (RewardHandler.getInstance().hasRewards(plugin.getSpecialRewardsConfig().getData(),
						plugin.getSpecialRewardsConfig().getFirstVoteRewardsPath())) {
					return "True";
				} else {
					return "False";
				}
			}
		}));
		metrics.addCustomChart(new BStatsMetrics.SimplePie("extrarewards_everysite", new Callable<String>() {

			@Override
			public String call() throws Exception {
				if (RewardHandler.getInstance().hasRewards(plugin.getConfigVoteSites().getData(),
						plugin.getConfigVoteSites().getEverySiteRewardPath())) {
					return "True";
				} else {
					return "False";
				}
			}
		}));
		metrics.addCustomChart(new BStatsMetrics.SimplePie("extrarewards_allsites", new Callable<String>() {

			@Override
			public String call() throws Exception {
				if (RewardHandler.getInstance().hasRewards(plugin.getSpecialRewardsConfig().getData(),
						plugin.getSpecialRewardsConfig().getAllSitesRewardPath())) {
					return "True";
				} else {
					return "False";
				}
			}
		}));
		metrics.addCustomChart(new BStatsMetrics.SimplePie("extrarewards_cumulative", new Callable<String>() {

			@Override
			public String call() throws Exception {
				if (plugin.getSpecialRewardsConfig().getCumulativeVotes().size() == 0) {
					return "False";
				} else {
					for (String cum : plugin.getSpecialRewardsConfig().getCumulativeVotes()) {
						if (plugin.getSpecialRewardsConfig().getCumulativeRewardEnabled(Integer.parseInt(cum))) {
							return "True";
						}
					}
					return "False";
				}
			}
		}));
		metrics.addCustomChart(new BStatsMetrics.SimplePie("extrarewards_voteparty", new Callable<String>() {

			@Override
			public String call() throws Exception {
				if (!plugin.getSpecialRewardsConfig().getVotePartyEnabled()) {
					return "False";
				} else {
					return "True";
				}
			}
		}));
		metrics.addCustomChart(new BStatsMetrics.SimplePie("extrarewards_milestone", new Callable<String>() {

			@Override
			public String call() throws Exception {
				if (plugin.getSpecialRewardsConfig().getMilestoneVotes().size() == 0) {
					return "False";
				} else {
					for (String milestone : plugin.getSpecialRewardsConfig().getMilestoneVotes()) {
						if (plugin.getSpecialRewardsConfig().getMilestoneRewardEnabled(Integer.parseInt(milestone))) {
							return "True";
						}
					}
					return "False";
				}
			}
		}));
		metrics.addCustomChart(new BStatsMetrics.SimplePie("extrarewards_anysitereward", new Callable<String>() {

			@Override
			public String call() throws Exception {
				if (RewardHandler.getInstance().hasRewards(plugin.getSpecialRewardsConfig().getData(),
						plugin.getSpecialRewardsConfig().getAnySiteRewardsPath())) {
					return "True";
				} else {
					return "False";
				}
			}
		}));
		metrics.addCustomChart(new BStatsMetrics.SimplePie("extrarewards_votestreakday", new Callable<String>() {

			@Override
			public String call() throws Exception {
				for (String s : plugin.getSpecialRewardsConfig().getVoteStreakVotes("Day")) {

					if (plugin.getSpecialRewardsConfig().getVoteStreakRewardEnabled("Day", s)
							&& RewardHandler.getInstance().hasRewards(plugin.getSpecialRewardsConfig().getData(),
									plugin.getSpecialRewardsConfig().getVoteStreakRewardsPath("Day", s))) {
						return "True";
					}

				}
				return "False";
			}
		}));

		metrics.addCustomChart(new BStatsMetrics.SimplePie("extrarewards_votestreakweek", new Callable<String>() {

			@Override
			public String call() throws Exception {
				for (String s : plugin.getSpecialRewardsConfig().getVoteStreakVotes("Week")) {

					if (plugin.getSpecialRewardsConfig().getVoteStreakRewardEnabled("Week", s)
							&& RewardHandler.getInstance().hasRewards(plugin.getSpecialRewardsConfig().getData(),
									plugin.getSpecialRewardsConfig().getVoteStreakRewardsPath("Week", s))) {
						return "True";
					}
				}

				return "False";
			}
		}));

		metrics.addCustomChart(new BStatsMetrics.SimplePie("extrarewards_votestreakmonth", new Callable<String>() {

			@Override
			public String call() throws Exception {
				for (String s : plugin.getSpecialRewardsConfig().getVoteStreakVotes("Month")) {

					if (plugin.getSpecialRewardsConfig().getVoteStreakRewardEnabled("Month", s)
							&& RewardHandler.getInstance().hasRewards(plugin.getSpecialRewardsConfig().getData(),
									plugin.getSpecialRewardsConfig().getVoteStreakRewardsPath("Month", s))) {
						return "True";
					}
				}

				return "False";
			}
		}));
		metrics.addCustomChart(new BStatsMetrics.SimplePie("numberofsites", new Callable<String>() {

			@Override
			public String call() throws Exception {
				return "" + plugin.getVoteSites().size();
			}
		}));
		metrics.addCustomChart(new BStatsMetrics.SimplePie("numberofrewards", new Callable<String>() {

			@Override
			public String call() throws Exception {
				return "" + RewardHandler.getInstance().getRewards().size();
			}
		}));
		metrics.addCustomChart(new BStatsMetrics.SimplePie("autocreatevotesites", new Callable<String>() {

			@Override
			public String call() throws Exception {
				return "" + plugin.getConfigFile().isAutoCreateVoteSites();
			}
		}));

		metrics.addCustomChart(new BStatsMetrics.SimplePie("numberofusers", new Callable<String>() {

			@Override
			public String call() throws Exception {
				int total = UserManager.getInstance().getAllUUIDs().size();
				if (total < 1000) {
					return "<1000";
				} else if (total > 10000) {
					return ">10000";
				} else {
					return "1000-10000";
				}
			}
		}));

		int users = UserManager.getInstance().getAllUUIDs().size();
		if (users > 1000) {
			metrics.addCustomChart(new BStatsMetrics.SimplePie("number_of_users_in_thousands", new Callable<String>() {

				@Override
				public String call() throws Exception {
					int total = UserManager.getInstance().getAllUUIDs().size() / 1000;
					return "" + total;
				}
			}));
		}
		if (users > 10000) {
			metrics.addCustomChart(new BStatsMetrics.SimplePie("number_of_users_in_thousands_plus_10", new Callable<String>() {

				@Override
				public String call() throws Exception {
					int total = UserManager.getInstance().getAllUUIDs().size() / 1000;
					return "" + total;
				}
			}));
		}
		metrics.addCustomChart(new BStatsMetrics.SimplePie("data_storage", new Callable<String>() {

			@Override
			public String call() throws Exception {
				return plugin.getOptions().getStorageType().toString();
			}
		}));
		metrics.addCustomChart(new BStatsMetrics.SimplePie("DisableCheckOnWorldChange", new Callable<String>() {

			@Override
			public String call() throws Exception {
				return "" + plugin.getOptions().isDisableCheckOnWorldChange();
			}
		}));
		metrics.addCustomChart(new BStatsMetrics.SimplePie("votereminding_enabled", new Callable<String>() {

			@Override
			public String call() throws Exception {
				return "" + plugin.getConfigFile().getVoteRemindingEnabled();
			}
		}));
		metrics.addCustomChart(new BStatsMetrics.SimplePie("LoadTopVoter_Monthly", new Callable<String>() {

			@Override
			public String call() throws Exception {
				return "" + plugin.getConfigFile().getLoadTopVoterMonthly();
			}
		}));
		metrics.addCustomChart(new BStatsMetrics.SimplePie("LoadTopVoter_Weekly", new Callable<String>() {

			@Override
			public String call() throws Exception {
				return "" + plugin.getConfigFile().getLoadTopVoterWeekly();
			}
		}));
		metrics.addCustomChart(new BStatsMetrics.SimplePie("LoadTopVoter_Daily", new Callable<String>() {

			@Override
			public String call() throws Exception {
				return "" + plugin.getConfigFile().getLoadTopVoterDaily();
			}
		}));
		metrics.addCustomChart(new BStatsMetrics.SimplePie("bungeemethod", new Callable<String>() {

			@Override
			public String call() throws Exception {
				if (plugin.getBungeeSettings().isUseBungeecoord()) {
					return "" + plugin.getBungeeHandler().getMethod().toString();
				} else {
					return "Disabled";
				}
			}
		}));
		if (plugin.getBungeeSettings().isUseBungeecoord()) {
			metrics.addCustomChart(new BStatsMetrics.SimplePie("bungeebroadcast", new Callable<String>() {

				@Override
				public String call() throws Exception {
					return "" + plugin.getBungeeSettings().isBungeeBroadcast();
				}
			}));
			metrics.addCustomChart(new BStatsMetrics.SimplePie("bungeebroadcastalways", new Callable<String>() {

				@Override
				public String call() throws Exception {
					return "" + plugin.getBungeeSettings().isBungeeBroadcastAlways();
				}
			}));

			metrics.addCustomChart(new BStatsMetrics.SimplePie("perserverrewards", new Callable<String>() {

				@Override
				public String call() throws Exception {
					return "" + plugin.getBungeeSettings().isPerServerRewards();
				}
			}));
			metrics.addCustomChart(new BStatsMetrics.SimplePie("perserverpoints", new Callable<String>() {

				@Override
				public String call() throws Exception {
					return "" + plugin.getBungeeSettings().isPerServerPoints();
				}
			}));
			metrics.addCustomChart(new BStatsMetrics.SimplePie("triggervotifierevent", new Callable<String>() {

				@Override
				public String call() throws Exception {
					return "" + plugin.getBungeeSettings().isTriggerVotifierEvent();
				}
			}));
		}

		metrics.addCustomChart(new BStatsMetrics.SimplePie("geyserprefixsupport", new Callable<String>() {

			@Override
			public String call() throws Exception {
				return "" + plugin.getOptions().isGeyserPrefixSupport();
			}
		}));

		metrics.addCustomChart(new BStatsMetrics.SimplePie("using_dev_build", new Callable<String>() {

			@Override
			public String call() throws Exception {
				return "" + plugin.getProfile().contains("dev");
			}
		}));

		metrics.addCustomChart(new BStatsMetrics.SimplePie("votepointtransfering", new Callable<String>() {

			@Override
			public String call() throws Exception {
				return "" + plugin.getConfigFile().isAllowVotePointTransfers();
			}
		}));

		metrics.addCustomChart(new BStatsMetrics.SimplePie("votecooldown_check_enabled", new Callable<String>() {

			@Override
			public String call() throws Exception {
				return "" + (!plugin.getConfigFile().isDisableCoolDownCheck() && RewardHandler.getInstance()
						.hasRewards(plugin.getSpecialRewardsConfig().getData(), "VoteCoolDownEndedReward"));
			}
		}));

		if (!plugin.getBuildNumber().equals("NOTSET")) {
			metrics.addCustomChart(new BStatsMetrics.SimplePie("dev_build_number", new Callable<String>() {

				@Override
				public String call() throws Exception {
					return "" + plugin.getBuildNumber();
				}
			}));
		}
	}
}
