package io.github.jolkert.adminutils.mixin;

import io.github.jolkert.adminutils.AdminUtils;
import net.minecraft.network.message.SignedMessage;
import net.minecraft.server.command.MessageCommand;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Mixin(MessageCommand.class)
public class MessageCommandMixin
{
	@Inject(at = @At("TAIL"), method = "execute")
	private static void logMessageToConsole(ServerCommandSource source, Collection<ServerPlayerEntity> targets, SignedMessage message, CallbackInfo ci)
	{
		String sender = source.getName();
		if (sender == null || sender.isEmpty())
			return;

		List<ServerPlayerEntity> players = new ArrayList<>(targets);
		for (ServerPlayerEntity player : players)
			AdminUtils.LOGGER.info("Whisper: [" + sender + " -> " + player.getName().getString() + "] " + message.getContent().getString());
	}
}
